package dev.marfien.minecraftonk8s.proxy.bungeecord;

import dev.marfien.minecraftonk8s.proxy.api.ProxyAgonesBridge;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BungeecordProxyBridge extends ProxyAgonesBridge implements Listener {

    private final Plugin plugin;
    private final ProxyServer proxyServer;
    private final Map<String, ServerInfo> servers;
    private final BungeeComponentSerializer serializer = BungeeComponentSerializer.get();

    public BungeecordProxyBridge(Plugin plugin) {
        this.plugin = plugin;
        this.proxyServer = plugin.getProxy();
        this.servers = this.proxyServer.getServers();
    }

    @Override
    protected void schedule(Duration delay, Runnable task) {
        this.proxyServer.getScheduler()
                .schedule(this.plugin, task, delay.toMillis(), TimeUnit.MILLISECONDS);
    }

    @EventHandler(priority = Byte.MAX_VALUE)
    private void onLogin(PreLoginEvent event) {
        if (event.isCancelled()) return;

        super.handleLogin().ifPresent(component -> {
            event.setCancelled(true);
            event.setReason(TextComponent.fromArray(this.serializer.serialize(component)));
        });
    }

    @Override
    protected void addServer(String name, InetSocketAddress address) {
        this.servers.put(name, this.proxyServer.constructServerInfo(name, address, null, false));
    }

    @Override
    protected void removeServer(String name, InetSocketAddress ignored) {
        this.servers.remove(name);
    }

    public void handleShutdown0() {
        super.handleShutdown();
    }
}
