package dev.marfien.minecraftonk8s.client.common.hook;

import java.util.UUID;

public interface PostPlayerConnectHook {

    void onPlayerConnected(UUID playerId);

}
