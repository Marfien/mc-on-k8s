package dev.marfien.minecraftonk8s.client.common.hook;

import net.kyori.adventure.text.Component;
import java.util.UUID;

public interface PlayerConnectHook {

    Component onPlayerConnecting(UUID playerID);

}
