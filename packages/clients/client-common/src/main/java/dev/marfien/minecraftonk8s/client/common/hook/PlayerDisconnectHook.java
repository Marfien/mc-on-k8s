package dev.marfien.minecraftonk8s.client.common.hook;

import java.util.UUID;

public interface PlayerDisconnectHook {

    void onPlayerDisconnect(UUID playerId);

}
