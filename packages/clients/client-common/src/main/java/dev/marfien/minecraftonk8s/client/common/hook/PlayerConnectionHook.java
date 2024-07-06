package dev.marfien.minecraftonk8s.client.common.hook;

import java.util.UUID;

public interface PlayerConnectionHook {

    default void onPlayerConnected(UUID playerId) {}
    default void onPlayerDisconnected(UUID playerId) {}

}
