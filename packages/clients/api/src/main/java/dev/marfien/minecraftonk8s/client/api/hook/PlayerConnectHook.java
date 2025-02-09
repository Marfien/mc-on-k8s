package dev.marfien.minecraftonk8s.client.api.hook;

import java.util.UUID;
import net.kyori.adventure.text.Component;

public interface PlayerConnectHook {

    Component onPlayerConnecting(UUID playerID);

}
