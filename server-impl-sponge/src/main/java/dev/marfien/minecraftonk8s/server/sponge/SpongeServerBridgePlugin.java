package dev.marfien.minecraftonk8s.server.sponge;

import dev.marfien.minecraftonk8s.server.api.SubServerAgonesBridge;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin("@project.id@")
public class SpongeServerBridgePlugin extends SubServerAgonesBridge {

    @Listener(order = Order.POST)
    public void onServerStarted(StartedEngineEvent<Server> event) {
        super.ready();
    }

    @Listener(order = Order.PRE)
    public void onServerStopping(StoppingEngineEvent<Server> event) {
        super.handleShutdown();
    }

    @Listener(order = Order.POST)
    public void onPlayerLogin(ServerSideConnectionEvent.Login event) {
        if (event.isCancelled()) return;

        super.handlePlayerLogin();
    }

}
