package dev.marfien.minecraftonk8s.server.bukkit;

import dev.marfien.minecraftonk8s.server.api.SubServerAgonesBridge;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitServerBridgePlugin extends JavaPlugin {

    private final SubServerAgonesBridge bridge = new BukkitServerBridge();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents((Listener) this.bridge, this);
        this.bridge.ready();
    }

    @Override
    public void onDisable() {
        this.bridge.handleShutdown();
    }
}
