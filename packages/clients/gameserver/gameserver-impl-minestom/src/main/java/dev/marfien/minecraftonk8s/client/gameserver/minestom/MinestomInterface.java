package dev.marfien.minecraftonk8s.client.gameserver.minestom;

import dev.marfien.minecraftonk8s.client.common.hook.PlayerConnectHook;
import dev.marfien.minecraftonk8s.client.common.hook.PlayerDisconnectHook;
import dev.marfien.minecraftonk8s.client.common.hook.PostPlayerConnectHook;
import dev.marfien.minecraftonk8s.client.proxy.agent.GameServerAgent;
import dev.marfien.minecraftonk8s.client.proxy.agent.GameServerInterface;
import dev.marfien.minecraftonk8s.client.proxy.agent.configuration.GameServerEnvironmentConfiguration;
import java.time.temporal.ChronoUnit;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.AsyncPlayerPreLoginEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;

public class MinestomInterface implements GameServerInterface {

    private final GameServerAgent<GameServerInterface, GameServerEnvironmentConfiguration> agent
            = new GameServerAgent<>(this, new GameServerEnvironmentConfiguration());

    @Override
    public int getPlayerCount() {
        return MinecraftServer.getConnectionManager().getOnlinePlayers().size();
    }

    @Override
    public int getPlayerCapacity() {
        // I don't think Minestom has a max player count
        return Integer.MAX_VALUE;
    }

    @Override
    public ScheduledTask scheduleTask(Runnable task, long delay, long period) {
        var scheduled = MinecraftServer.getSchedulerManager()
                .buildTask(task)
                .delay(delay, ChronoUnit.SECONDS)
                .repeat(period, ChronoUnit.SECONDS)
                .schedule();
        return scheduled::cancel;
    }

    @Override
    public ScheduledTask scheduleTask(Runnable task, long delay) {
        var scheduled = MinecraftServer.getSchedulerManager()
                .buildTask(task)
                .delay(delay, ChronoUnit.SECONDS)
                .schedule();
        return scheduled::cancel;
    }

    @Override
    public RegisteredHook addHook(PlayerConnectHook hook) {
        EventNode<Event> eventNode = MinecraftServer.getGlobalEventHandler().addListener(AsyncPlayerPreLoginEvent.class, event -> {
                    Component component = hook.onPlayerConnecting(event.getPlayerUuid());

                    if (component != null) {
                        event.getPlayer().kick(component);
                    }
                });
        return () -> MinecraftServer.getGlobalEventHandler().removeChild(eventNode);
    }

    @Override
    public RegisteredHook addHook(PostPlayerConnectHook hook) {
        EventNode<Event> eventNode = MinecraftServer.getGlobalEventHandler().addListener(PlayerSpawnEvent.class, event -> {
                    if (event.isFirstSpawn()) {
                        hook.onPlayerConnected(event.getPlayer().getUuid());
                    }
                });
        return () -> MinecraftServer.getGlobalEventHandler().removeChild(eventNode);
    }

    @Override
    public RegisteredHook addHook(PlayerDisconnectHook hook) {
        EventNode<Event> eventNode = MinecraftServer.getGlobalEventHandler().addListener(PlayerDisconnectEvent.class, event -> {
                    hook.onPlayerDisconnect(event.getPlayer().getUuid());
                });
        return () -> MinecraftServer.getGlobalEventHandler().removeChild(eventNode);
    }

}
