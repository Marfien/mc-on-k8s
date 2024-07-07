package dev.marfien.minecraftonk8s.client.proxy.agent;

import dev.marfien.minecraftonk8s.client.common.ClientAgent;
import dev.marfien.minecraftonk8s.client.proxy.agent.configuration.ProxyConfiguration;
import dev.marfien.minecraftonk8s.client.proxy.agent.internal.DrainageHook;
import dev.marfien.minecraftonk8s.client.proxy.agent.internal.MinecraftServerInformer;
import net.kyori.adventure.text.Component;

public class ProxyAgent<I extends ProxyInterface, C extends ProxyConfiguration> extends
        ClientAgent<I, C> {

    private final MinecraftServerInformer informer;
    private boolean isDraining = false;

    protected ProxyAgent(I clientInterface, C configuration) {
        super(clientInterface, configuration);

        this.informer = new MinecraftServerInformer(
                clientInterface,
                configuration.getRebuildCacheInterval().toMillis(),
                configuration.getLabelSelector()
        );
    }

    public boolean isDraining() {
        return this.isDraining;
    }

    @Override
    public void onStartup() {
        super.onStartup();
        this.informer.start(
                super.configuration.getWatchingNamespace() == null
                        ? super.getKubernetesAdapter().getNamespace()
                        : super.configuration.getWatchingNamespace()
        );

        super.getKubernetesAdapter().self(podResource ->
                podResource.edit(pod -> pod.edit()
                        .editMetadata()
                        .addToLabels("mconk8s.marfien.dev/state", "running")
                        .endMetadata()
                        .build()
        ));
        this.clientInterface.scheduleTask(
                this::startDrainage,
                this.configuration.getDrainageDelay().toSeconds()
        );
    }

    @Override
    public void onShutdown() {
        this.informer.stop();
        super.onShutdown();
    }

    public void startDrainage() {
        isDraining = true;

        super.getKubernetesAdapter().self(
                podResource ->
                        podResource.edit(pod -> pod.edit()
                                .editMetadata()
                                .addToLabels("mconk8s.marfien.dev/state", "draining")
                                .endMetadata()
                                .build()));
        super.logger.info("Start draining players. No new players are accepted on this proxy...");
        super.clientInterface.addHook(new DrainageHook(this, this.clientInterface));

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
        }, this.configuration.getDrainageDuration().toSeconds());
    }

}
