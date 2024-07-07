package dev.marfien.minecraftonk8s.client.common.hook;

import net.kyori.adventure.text.Component;
import java.util.UUID;

public interface PlayerConnectionHook {

    default Component onPlayerConnect(UUID playerId) {
        return null;
    }

    default void onPlayerConnected(UUID playerId) {}

    default void onPlayerDisconnect(UUID playerId) {

    }

    default void onPlayerDisconnected(UUID playerId) {

    }

}
