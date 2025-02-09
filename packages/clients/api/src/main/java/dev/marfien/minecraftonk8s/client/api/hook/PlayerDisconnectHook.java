package dev.marfien.minecraftonk8s.client.api.hook;

import java.util.UUID;

public interface PlayerDisconnectHook {

    void onPlayerDisconnect(UUID playerId);

}
