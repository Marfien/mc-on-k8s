package dev.marfien.minecraftonk8s.client.proxy.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.marfien.minecraftonk8s.client.proxy.agent.ProxyAgent;
import dev.marfien.minecraftonk8s.client.proxy.agent.configuration.ProxyEnvironmentConfiguration;
import jakarta.inject.Inject;

public class VelocityProxyAgent extends ProxyAgent<VelocityProxyInterface, ProxyEnvironmentConfiguration> {

    @Inject
    public VelocityProxyAgent(ProxyServer proxyServer) {
        super(new VelocityProxyInterface(proxyServer), new ProxyEnvironmentConfiguration());
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        super.onStartup();
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        super.onShutdown();
    }

}
