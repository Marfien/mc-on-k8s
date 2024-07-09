package dev.marfien.minecraftonk8s.client.gameserver.bukkit;

import dev.marfien.minecraftonk8s.client.common.hook.PlayerConnectHook;
import dev.marfien.minecraftonk8s.client.common.hook.PlayerDisconnectHook;
import dev.marfien.minecraftonk8s.client.common.hook.PostPlayerConnectHook;
import dev.marfien.minecraftonk8s.client.proxy.agent.ProxyInterface;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.protocol.ProtocolConstants;

public class BungeecordProxyInterface implements ProxyInterface {

    private final ProxyServer proxyServer = ProxyServer.getInstance();
    private final Map<String, ServerInfo> serverInfoMap = this.proxyServer.getServers();
    private final Plugin plugin;

    public BungeecordProxyInterface(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void addServer(String serverName, String address, int port) {
        this.serverInfoMap.put(
                serverName,
                this.proxyServer.constructServerInfo(
                        serverName,
                        new InetSocketAddress(address, port),
                        null,
                        false
                )
        );
    }

    @Override
    public void removeServer(String serverName) {
        this.serverInfoMap.remove(serverName);
    }

    @Override
    public boolean hasServer(String serverName) {
        return this.serverInfoMap.containsKey(serverName);
    }

    @Override
    public void kickAll(Component s) {
        for (ProxiedPlayer player : this.proxyServer.getPlayers()) {
            player.disconnect(getSerializerFor(player.getPendingConnection()).serialize(s));
        }
    }

    @Override
    public int getPlayerCount() {
        return this.proxyServer.getOnlineCount();
    }

    @Override
    public int getPlayerCapacity() {
        return this.proxyServer.getConfig().getPlayerLimit();
    }

    @Override
    public ScheduledTask scheduleTask(Runnable task, long delay, long period) {
        var scheduledTask = this.proxyServer.getScheduler().schedule(
                this.plugin,
                task,
                delay,
                period,
                TimeUnit.SECONDS
        );
        return scheduledTask::cancel;
    }

    @Override
    public ScheduledTask scheduleTask(Runnable task, long delay) {
        var scheduledTask = this.proxyServer.getScheduler().schedule(
                this.plugin,
                task,
                delay,
                TimeUnit.SECONDS
        );
        return scheduledTask::cancel;
    }

    @Override
    public RegisteredHook addHook(PlayerConnectHook hook) {
        EventWrapper<LoginEvent> listener = event -> {
            PendingConnection connection = event.getConnection();
            Component component = hook.onPlayerConnecting(connection.getUniqueId());
            if (component == null) return;

            event.setCancelled(true);
            event.setReason(
                    new TextComponent(
                            getSerializerFor(connection).serialize(component)
                    )
            );
        };

        PluginManager pluginManager = this.proxyServer.getPluginManager();
        pluginManager.registerListener(this.plugin, listener);

        return () -> pluginManager.unregisterListener(listener);
    }

    @Override
    public RegisteredHook addHook(PostPlayerConnectHook hook) {
        EventWrapper<PostLoginEvent> listener = event -> {
            hook.onPlayerConnected(event.getPlayer().getUniqueId());
        };

        PluginManager pluginManager = this.proxyServer.getPluginManager();
        pluginManager.registerListener(this.plugin, listener);

        return () -> pluginManager.unregisterListener(listener);
    }

    @Override
    public RegisteredHook addHook(PlayerDisconnectHook hook) {
        EventWrapper<PlayerDisconnectEvent> listener = event -> {
            hook.onPlayerDisconnect(event.getPlayer().getUniqueId());
        };

        PluginManager pluginManager = this.proxyServer.getPluginManager();
        pluginManager.registerListener(this.plugin, listener);

        return () -> pluginManager.unregisterListener(listener);
    }

    private static BungeeComponentSerializer getSerializerFor(PendingConnection playerConnection) {
        return playerConnection.getVersion() < 713 // Snapshot 20w17a introduced hex colors
                ? BungeeComponentSerializer.legacy()
                : BungeeComponentSerializer.get();
    }

    private interface EventWrapper<T extends Event> extends Listener {

        @EventHandler
        void handle(T event);

    }

}
