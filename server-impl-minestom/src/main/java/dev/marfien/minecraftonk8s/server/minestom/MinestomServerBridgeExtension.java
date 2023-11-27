package dev.marfien.minecraftonk8s.server.minestom;

import dev.marfien.minecraftonk8s.server.api.SubServerAgonesBridge;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extensions.Extension;

public class MinestomServerBridgeExtension extends Extension {

    private final SubServerAgonesBridge bridge = new MinestomServerBrdige();

    private final GlobalEventHandler masterNode = MinecraftServer.getGlobalEventHandler();

    @Override
    public void initialize() {
        this.bridge.ready();
        this.masterNode.addListener(PlayerLoginEvent.class, (MinestomServerBrdige) this.bridge);
    }

    @Override
    public void terminate() {
        this.bridge.handleShutdown();
    }
}
