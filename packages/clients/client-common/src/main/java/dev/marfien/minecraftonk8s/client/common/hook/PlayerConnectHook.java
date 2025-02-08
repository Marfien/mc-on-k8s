package dev.marfien.minecraftonk8s.client.common.hook;

import java.util.UUID;
import net.kyori.adventure.text.Component;

public interface PlayerConnectHook {

    Component onPlayerConnecting(UUID playerID);

}
