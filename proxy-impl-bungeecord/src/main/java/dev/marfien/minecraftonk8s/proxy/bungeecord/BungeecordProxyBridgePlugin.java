package dev.marfien.minecraftonk8s.proxy.bungeecord;

import net.md_5.bungee.api.plugin.Plugin;

public class BungeecordProxyBridgePlugin extends Plugin {

  private final BungeecordProxyBridge bridge = new BungeecordProxyBridge(this);

  @Override
  public void onEnable() {
    this.bridge.ready();
    super.getProxy().getPluginManager().registerListener(this, this.bridge);
  }

  @Override
  public void onDisable() {
    this.bridge.handleShutdown();
  }
}
