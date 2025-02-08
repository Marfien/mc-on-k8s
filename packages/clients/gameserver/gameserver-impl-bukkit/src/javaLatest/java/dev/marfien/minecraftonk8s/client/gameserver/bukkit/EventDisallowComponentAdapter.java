package dev.marfien.minecraftonk8s.client.gameserver.bukkit;

import net.kyori.adventure.text.Component;
import org.bukkit.event.player.PlayerLoginEvent;

public class EventDisallowComponentAdapter {


    public static void disallow(PlayerLoginEvent event, Component component) {
        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, component);
    }
}
