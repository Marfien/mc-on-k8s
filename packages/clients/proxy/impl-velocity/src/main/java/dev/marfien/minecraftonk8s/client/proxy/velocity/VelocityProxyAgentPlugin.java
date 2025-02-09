package dev.marfien.minecraftonk8s.client.proxy.velocity;

import com.velocitypowered.api.event.EventHandler;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.ResultedEvent.ComponentResult;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import dev.marfien.minecraftonk8s.client.api.hook.PlayerConnectHook;
import dev.marfien.minecraftonk8s.client.api.hook.PlayerDisconnectHook;
import dev.marfien.minecraftonk8s.client.api.hook.PostPlayerConnectHook;
import dev.marfien.minecraftonk8s.client.proxy.agent.ProxyAgent;
import dev.marfien.minecraftonk8s.client.proxy.agent.ProxyInterface;
import dev.marfien.minecraftonk8s.client.proxy.agent.configuration.ProxyEnvironmentConfiguration;
import jakarta.inject.Inject;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import net.kyori.adventure.text.Component;

public class VelocityProxyAgentPlugin implements ProxyInterface {

    private final ProxyAgent<ProxyInterface, ProxyEnvironmentConfiguration> agent = new ProxyAgent<>(this, new ProxyEnvironmentConfiguration());
    private final ProxyServer proxyServer;

    @Inject
    public VelocityProxyAgentPlugin(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        this.agent.onStartup();
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        this.agent.onShutdown();
    }

    @Override
    public void addServer(String serverName, String address, int port) {
        this.proxyServer.registerServer(
                new ServerInfo(
                        serverName,
                        new InetSocketAddress(address, port)
                )
        );
    }

    @Override
    public void removeServer(String serverName) {
        this.proxyServer.unregisterServer(
                new ServerInfo(serverName, InetSocketAddress.createUnresolved("unused", 0xFFFF)));
    }

    @Override
    public boolean hasServer(String serverName) {
        return this.proxyServer.getServer(serverName).isPresent();
    }

    @Override
    public void kickAll(Component message) {
        this.proxyServer.getAllPlayers().forEach(player -> player.disconnect(message));
    }

    @Override
    public int getPlayerCount() {
        return this.proxyServer.getPlayerCount();
    }

    @Override
    public int getPlayerCapacity() {
        // Meh
        return this.proxyServer.getConfiguration().getShowMaxPlayers();
    }

    @Override
    public ScheduledTask scheduleTask(Runnable task, long delay, long period) {
        var scheduledTask = this.proxyServer.getScheduler()
                .buildTask(this, task)
                .delay(delay, TimeUnit.SECONDS)
                .repeat(period, TimeUnit.SECONDS)
                .schedule();
        return scheduledTask::cancel;
    }

    @Override
    public ScheduledTask scheduleTask(Runnable task, long delay) {
        var scheduledTask = this.proxyServer.getScheduler()
                .buildTask(this, task)
                .delay(delay, TimeUnit.SECONDS)
                .schedule();
        return scheduledTask::cancel;
    }

    @Override
    public RegisteredHook addHook(PlayerConnectHook hook) {
        return this.registerEvent(LoginEvent.class, event -> {
            Component component = hook.onPlayerConnecting(event.getPlayer().getUniqueId());

            if (component != null) {
                event.setResult(ComponentResult.denied(component));
            }
        });
    }

    @Override
    public RegisteredHook addHook(PostPlayerConnectHook hook) {
        return this.registerEvent(PostLoginEvent.class, event -> {
            hook.onPlayerConnected(event.getPlayer().getUniqueId());
        });
    }

    @Override
    public RegisteredHook addHook(PlayerDisconnectHook hook) {
        return registerEvent(DisconnectEvent.class, event -> {
            hook.onPlayerDisconnect(event.getPlayer().getUniqueId());
        });
    }

    private <E> RegisteredHook registerEvent(Class<E> eventClass, EventHandler<E> handler) {
        EventManager eventManager = this.proxyServer.getEventManager();
        eventManager.register(this, eventClass, handler);

        return () -> eventManager.unregister(this, handler);
    }

}
