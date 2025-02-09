package dev.marfien.minecraftonk8s.client.gameserver.bukkit;

import dev.marfien.minecraftonk8s.client.api.hook.PlayerConnectHook;
import dev.marfien.minecraftonk8s.client.api.hook.PlayerDisconnectHook;
import dev.marfien.minecraftonk8s.client.api.hook.PostPlayerConnectHook;
import dev.marfien.minecraftonk8s.client.proxy.agent.ProxyAgent;
import dev.marfien.minecraftonk8s.client.proxy.agent.ProxyInterface;
import dev.marfien.minecraftonk8s.client.proxy.agent.configuration.ProxyEnvironmentConfiguration;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
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

public class BungeecordAgentPlugin extends Plugin implements ProxyInterface {

    private final ProxyAgent<ProxyInterface, ProxyEnvironmentConfiguration> agent
            = new ProxyAgent<>(this, new ProxyEnvironmentConfiguration());

    private final ProxyServer proxyServer = ProxyServer.getInstance();
    private final Map<String, ServerInfo> serverInfoMap = this.proxyServer.getServers();

    @Override
    public void onEnable() {
        this.agent.onStartup();
    }

    @Override
    public void onDisable() {
        this.agent.onShutdown();
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
                this,
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
                this,
                task,
                delay,
                TimeUnit.SECONDS
        );
        return scheduledTask::cancel;
    }

    @Override
    public RegisteredHook addHook(PlayerConnectHook hook) {
        return this.registerEvent(LoginEvent.class, event -> {
            PendingConnection connection = event.getConnection();
            Component component = hook.onPlayerConnecting(connection.getUniqueId());
            if (component == null) return;

            event.setCancelled(true);
            event.setReason(
                    new TextComponent(
                            getSerializerFor(connection).serialize(component)
                    )
            );
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
        return this.registerEvent(PlayerDisconnectEvent.class, event -> {
            hook.onPlayerDisconnect(event.getPlayer().getUniqueId());
        });
    }

    private static BungeeComponentSerializer getSerializerFor(PendingConnection playerConnection) {
        return playerConnection.getVersion() < 713 // Snapshot 20w17a introduced hex colors
                ? BungeeComponentSerializer.legacy()
                : BungeeComponentSerializer.get();
    }

    private <E extends Event> RegisteredHook registerEvent(Class<E> eventClass, Consumer<E> hook) {
        PluginManager pluginManager = this.proxyServer.getPluginManager();

        Listener listener = new Listener() {
            @EventHandler
            public void onEvent(E event) {
                hook.accept(event);
            }
        };
        pluginManager.registerListener(this, listener);

        return () -> pluginManager.unregisterListener(listener);

    }
}
