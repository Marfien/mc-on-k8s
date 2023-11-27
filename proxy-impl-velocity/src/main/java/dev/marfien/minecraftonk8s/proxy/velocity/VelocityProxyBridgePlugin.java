package dev.marfien.minecraftonk8s.proxy.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import com.velocitypowered.api.scheduler.Scheduler;
import dev.marfien.minecraftonk8s.proxy.api.ProxyAgonesBridge;

import java.net.InetSocketAddress;
import java.time.Duration;

@Plugin(
        id = "@project.id@",
        name = "@project.name@",
        version = "@project.version@",
        description = "@project.description@"
)
public class VelocityProxyBridgePlugin extends ProxyAgonesBridge {

    private final Scheduler scheduler;
    private final ProxyServer proxyServer;

    @Inject
    public VelocityProxyBridgePlugin(Scheduler scheduler, ProxyServer proxyServer) {
        this.scheduler = scheduler;
        this.proxyServer = proxyServer;
    }

    @Subscribe
    public void onInit(ProxyInitializeEvent event) {
        super.ready();
    }

    @Subscribe
    public void onShutdown(ProxyShutdownEvent event) {
        super.handleShutdown();
    }

    @Subscribe
    public void onPlayerJoin(PreLoginEvent event) {
        super.handleLogin().ifPresent(component ->
                event.setResult(PreLoginEvent.PreLoginComponentResult.denied(component)));
    }

    @Override
    protected void schedule(Duration delay, Runnable task) {
        this.scheduler.buildTask(this, task)
                .delay(delay)
                .clearRepeat()
                .schedule();
    }

    @Override
    protected void addServer(String name, InetSocketAddress address) {
        this.proxyServer.registerServer(new ServerInfo(name, address));
    }

    @Override
    protected void removeServer(String name, InetSocketAddress address) {
        this.proxyServer.unregisterServer(new ServerInfo(name, address));
    }
}
