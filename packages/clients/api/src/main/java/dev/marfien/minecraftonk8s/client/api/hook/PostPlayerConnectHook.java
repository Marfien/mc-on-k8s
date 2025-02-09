package dev.marfien.minecraftonk8s.client.api.hook;

import java.util.UUID;

public interface PostPlayerConnectHook {

    void onPlayerConnected(UUID playerId);

}
