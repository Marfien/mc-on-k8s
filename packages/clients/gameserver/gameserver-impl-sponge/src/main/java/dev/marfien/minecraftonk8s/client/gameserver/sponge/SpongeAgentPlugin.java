package dev.marfien.minecraftonk8s.client.gameserver.sponge;

import com.google.inject.Inject;
import dev.marfien.minecraftonk8s.client.common.hook.PlayerConnectHook;
import dev.marfien.minecraftonk8s.client.common.hook.PlayerDisconnectHook;
import dev.marfien.minecraftonk8s.client.common.hook.PostPlayerConnectHook;
import dev.marfien.minecraftonk8s.client.proxy.agent.GameServerAgent;
import dev.marfien.minecraftonk8s.client.proxy.agent.GameServerInterface;
import dev.marfien.minecraftonk8s.client.proxy.agent.configuration.GameServerEnvironmentConfiguration;
import java.time.Duration;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.EventListenerRegistration;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin("minecraftonk8s-agent")
public class SpongeAgentPlugin implements GameServerInterface {

    private final GameServerAgent<GameServerInterface, GameServerEnvironmentConfiguration> agent
            = new GameServerAgent<>(this, new GameServerEnvironmentConfiguration());

    @Inject
    private Game game;

    @Inject
    private PluginContainer pluginContainer;

    @Listener
    public void onEnable(StartedEngineEvent<Server> event) {
        this.agent.onStartup();
    }

    @Listener
    public void onDisable(StoppingEngineEvent<Server> event) {
        this.agent.onShutdown();
    }

    @Override
    public int getPlayerCount() {
        return this.game.server().onlinePlayers().size();
    }

    @Override
    public int getPlayerCapacity() {
        return this.game.server().maxPlayers();
    }

    @Override
    public ScheduledTask scheduleTask(Runnable task, long delay, long period) {
        var scheduledTask = this.game.asyncScheduler().submit(Task.builder()
                .execute(task)
                .delay(Duration.ofSeconds(delay))
                .interval(Duration.ofSeconds(period))
                .build());
        return scheduledTask::cancel;
    }

    @Override
    public ScheduledTask scheduleTask(Runnable task, long delay) {
        var scheduledTask = this.game.asyncScheduler().submit(Task.builder()
                .execute(task)
                .delay(Duration.ofSeconds(delay))
                .build());
        return scheduledTask::cancel;
    }

    @Override
    public RegisteredHook addHook(PlayerConnectHook hook) {
        return this.registerEvent(ServerSideConnectionEvent.Login.class, event -> {
            Component component = hook.onPlayerConnecting(event.profile().uniqueId());
            if (component != null) {
                event.setMessage(component);
                event.setCancelled(true);
            }
        });
    }

    @Override
    public RegisteredHook addHook(PostPlayerConnectHook hook) {
        return this.registerEvent(ServerSideConnectionEvent.Join.class, event -> {
            hook.onPlayerConnected(event.player().uniqueId());
        });
    }

    @Override
    public RegisteredHook addHook(PlayerDisconnectHook hook) {
        return this.registerEvent(ServerSideConnectionEvent.Disconnect.class, event -> {
            hook.onPlayerDisconnect(event.player().uniqueId());
        });
    }

    private <E extends Event> RegisteredHook registerEvent(Class<E> eventClass, EventListener<E> consumer) {
        EventManager eventManager = this.game.eventManager();
        EventListenerRegistration<E> registration = EventListenerRegistration.builder(eventClass)
                .listener(consumer)
                .plugin(this.pluginContainer)
                .build();
        eventManager.registerListener(registration);
        return () -> eventManager.registerListener(registration);
    }

}
