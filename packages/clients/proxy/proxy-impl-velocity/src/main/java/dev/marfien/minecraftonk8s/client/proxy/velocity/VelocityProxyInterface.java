package dev.marfien.minecraftonk8s.client.proxy.velocity;

import com.velocitypowered.api.event.EventHandler;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.ResultedEvent.ComponentResult;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import dev.marfien.minecraftonk8s.client.common.hook.PlayerConnectHook;
import dev.marfien.minecraftonk8s.client.common.hook.PlayerDisconnectHook;
import dev.marfien.minecraftonk8s.client.common.hook.PostPlayerConnectHook;
import dev.marfien.minecraftonk8s.client.proxy.agent.ProxyInterface;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import net.kyori.adventure.text.Component;

public class VelocityProxyInterface implements ProxyInterface {

    private final ProxyServer proxyServer;

    public VelocityProxyInterface(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
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
        EventHandler<LoginEvent> handler = event -> {
            Component component = hook.onPlayerConnecting(event.getPlayer().getUniqueId());
            if (component != null) {
                event.setResult(ComponentResult.denied(component));
            }
        };

        EventManager eventManager = this.proxyServer.getEventManager();
        eventManager.register(this, LoginEvent.class, handler);

        return () -> eventManager.unregister(this, handler);
    }

    @Override
    public RegisteredHook addHook(PostPlayerConnectHook hook) {
        EventHandler<PostLoginEvent> handler = event -> {
            hook.onPlayerConnected(event.getPlayer().getUniqueId());
        };

        EventManager eventManager = this.proxyServer.getEventManager();
        eventManager.register(this, PostLoginEvent.class, handler);

        return () -> eventManager.unregister(this, handler);
    }

    @Override
    public RegisteredHook addHook(PlayerDisconnectHook hook) {
        EventHandler<DisconnectEvent> handler = event -> {
            hook.onPlayerDisconnect(event.getPlayer().getUniqueId());
        };

        EventManager eventManager = this.proxyServer.getEventManager();
        eventManager.register(this, DisconnectEvent.class, handler);

        return () -> eventManager.unregister(this, handler);
    }
}
