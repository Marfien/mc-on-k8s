package dev.marfien.minecraftonk8s.client.gameserver.bukkit;

import dev.marfien.minecraftonk8s.client.common.hook.PlayerConnectHook;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import java.util.function.Consumer;

public class PlayerConnectHookHandler implements Consumer<PlayerLoginEvent> {

    private final PlayerConnectHook hook;

    public PlayerConnectHookHandler(PlayerConnectHook hook) {
        this.hook = hook;
    }

    @Override
    public void accept(PlayerLoginEvent event) {
        Component component = this.hook.onPlayerConnecting(event.getPlayer().getUniqueId());

        if (component == null) {
            return;
        }

        event.disallow(
                Result.KICK_OTHER,
                component
        );
    }

}
