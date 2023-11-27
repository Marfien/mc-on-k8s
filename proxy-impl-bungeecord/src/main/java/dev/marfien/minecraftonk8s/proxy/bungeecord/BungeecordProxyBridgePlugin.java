package dev.marfien.minecraftonk8s.proxy.bungeecord;

import net.md_5.bungee.api.plugin.Plugin;

public class BungeecordProxyBridgePlugin extends Plugin {

    private final BungeecordProxyBridge connector = new BungeecordProxyBridge(this);

    @Override
    public void onLoad() {
        this.connector.ready();
    }

    @Override
    public void onEnable() {
        super.getProxy().getPluginManager().registerListener(this, this.connector);
    }

    @Override
    public void onDisable() {
        this.connector.handleShutdown();
    }

}
