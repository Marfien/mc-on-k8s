package dev.marfien.minecraftonk8s.client.proxy.agent;

import dev.marfien.minecraftonk8s.client.common.ClientAgent;
import dev.marfien.minecraftonk8s.client.proxy.agent.configuration.ProxyConfiguration;
import dev.marfien.minecraftonk8s.client.proxy.agent.drainage.hook.DrainageHook;
import dev.marfien.minecraftonk8s.client.proxy.agent.internal.MinecraftServerInformer;
import net.kyori.adventure.text.Component;

public class ProxyAgent<I extends ProxyInterface, C extends ProxyConfiguration> extends ClientAgent<I, C> {

    private final MinecraftServerInformer informer;
    private boolean isDraining = false;

    protected ProxyAgent(I clientInterface, C configuration) {
        super(clientInterface, configuration);

        this.informer = new MinecraftServerInformer(
                clientInterface,
                configuration.getRebuildCacheInterval().toMillis()
        );
    }

    public boolean isDraining() {
        return isDraining;
    }

    @Override
    public void onStartup() {
        super.onStartup();
        this.informer.start(
                super.configuration.getWatchingNamespace() == null
                        ? super.getNamespace()
                        : super.configuration.getWatchingNamespace()
        );

        // Drainage
        this.clientInterface.scheduleTask(() -> {
            isDraining = true;

            super.logger.info("Start draining players. No new players are accepted on this proxy...");
            super.clientInterface.addHook(new DrainageHook(this, this.clientInterface));

            if (super.clientInterface.getPlayerCount() == 0) {
                super.logger.info("Proxy is already empty. Shutting down...");
                super.shutdown();
                return;
            }

            this.clientInterface.scheduleTask(() -> {
                super.logger.warn("Draining duration exceeded. Kicking all players and shutting down...");
                super.clientInterface.kickAll(Component.text("You've played long enough. Touch some grass now!"));
                super.shutdown();
            }, this.configuration.getDrainageDuration().toSeconds());
        }, this.configuration.getDrainageDelay().toSeconds());
    }

    @Override
    public void onShutdown() {
        this.informer.stop();
        super.onShutdown();
    }
}
