package dev.marfien.minecraftonk8s.client.proxy.agent;

import dev.marfien.minecraftonk8s.client.api.ClientAgent;
import dev.marfien.minecraftonk8s.client.api.hook.PlayerDisconnectHook;
import dev.marfien.minecraftonk8s.client.proxy.agent.configuration.ProxyConfiguration;
import dev.marfien.minecraftonk8s.client.proxy.agent.internal.MinecraftServerInformer;
import dev.marfien.minecraftonk8s.common.Constant.AppLabel;
import dev.marfien.minecraftonk8s.common.Constant.ProxyState;
import java.util.concurrent.TimeUnit;
import net.kyori.adventure.text.Component;

public class ProxyAgent<I extends ProxyInterface, C extends ProxyConfiguration> extends
        ClientAgent<I, C> {

    private final MinecraftServerInformer informer;
    private boolean isDraining = false;

    public ProxyAgent(I clientInterface, C configuration) {
        super(clientInterface, configuration);

        this.informer = new MinecraftServerInformer(
                clientInterface,
                TimeUnit.MINUTES.toMillis(configuration.getRebuildCacheIntervalMinutes()),
                configuration.getLabelSelector()
        );
    }

    public boolean isDraining() {
        return this.isDraining;
    }

    @Override
    public void onStartup() {
        super.onStartup();
        this.informer.start();

        super.getKubernetesAdapter().self(podResource ->
                podResource.edit(pod -> pod.edit()
                        .editMetadata()
                            .addToLabels(AppLabel.PROXY_STATE, ProxyState.RUNNING)
                            .endMetadata()
                        .build()
        ));
        this.clientInterface.scheduleTask(
                this::startDrainage,
                TimeUnit.HOURS.toSeconds(this.configuration.getDrainageDelayHours())
        );
    }

    @Override
    public void onShutdown() {
        this.informer.stop();
        super.onShutdown();
    }

    public void startDrainage() {
        this.isDraining = true;

        // Add label to the pod to indicate that the proxy is draining
        // This is important for any service referring to this pod to know that the pod should not be used for new connections
        super.getKubernetesAdapter().self(
                podResource ->
                        podResource.edit(pod -> pod.edit()
                                .editMetadata()
                                    .addToLabels(AppLabel.PROXY_STATE, ProxyState.DRAINING)
                                    .endMetadata()
                                .build()));
        super.logger.info("Start draining players. No new players are accepted on this proxy...");

        super.clientInterface.addHook(playerId -> {
            if (this.isDraining) {
                this.logger.info("Player {} tried to connect while proxy is draining. Kicking...", playerId);
                return Component.text("Proxy is draining. No new players are accepted.");
            }

            return null;
        });
        super.clientInterface.addHook((PlayerDisconnectHook) uuid -> {
            super.clientInterface.scheduleTask(() -> {
                if (!this.isDraining) return;

                if (super.clientInterface.getPlayerCount() == 0) {
                    this.logger.info("Proxy is empty. Shutting down...");
                    super.shutdown();
                }
            }, 1);
        });

        if (super.clientInterface.getPlayerCount() == 0) {
            super.logger.info("Proxy is already empty. Shutting down...");
            super.shutdown();
            return;
        }

        this.clientInterface.scheduleTask(() -> {
            super.logger.warn(
                    "Draining duration exceeded. Kicking all players and shutting down...");
            super.clientInterface.kickAll(
                    Component.text("You've played long enough. Touch some grass now!"));
            super.shutdown();
        }, TimeUnit.HOURS.toSeconds(this.configuration.getDrainageTimeoutHours()));
    }

}
