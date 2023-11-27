package dev.marfien.minecraftonk8s.server.bukkit;

import dev.marfien.minecraftonk8s.server.api.SubServerAgonesBridge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class BukkitServerBridge extends SubServerAgonesBridge implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(PlayerLoginEvent event) {
        super.handlePlayerLogin();
    }

}
