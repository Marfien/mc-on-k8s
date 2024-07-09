package dev.marfien.minecraftonk8s.client.gameserver.bukkit;

import dev.marfien.minecraftonk8s.client.common.hook.PlayerConnectHook;
import dev.marfien.minecraftonk8s.client.common.hook.PlayerDisconnectHook;
import dev.marfien.minecraftonk8s.client.common.hook.PostPlayerConnectHook;
import dev.marfien.minecraftonk8s.client.proxy.agent.GameServerAgent;
import dev.marfien.minecraftonk8s.client.proxy.agent.GameServerInterface;
import dev.marfien.minecraftonk8s.client.proxy.agent.configuration.GameServerEnvironmentConfiguration;
import java.util.function.Consumer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitAgentPlugin extends JavaPlugin implements GameServerInterface {

    private final GameServerAgent<GameServerInterface, GameServerEnvironmentConfiguration> agent
            = new GameServerAgent<>(this, new GameServerEnvironmentConfiguration());

    private static final boolean ADVENTURE_TEXT_SUPPORTED;

    static {
        boolean supported = true;
        try {
            Class<PlayerLoginEvent> clazz = PlayerLoginEvent.class;
            clazz.getMethod("disallow", Result.class, Component.class);
        } catch (NoSuchMethodException e) {
            supported = false;
        }

        ADVENTURE_TEXT_SUPPORTED = supported;
    }

    @Override
    public void onEnable() {
        this.agent.onStartup();
    }

    @Override
    public void onDisable() {
        this.agent.onShutdown();
    }

    @Override
    public int getPlayerCount() {
        return this.getServer().getOnlinePlayers().size();
    }

    @Override
    public int getPlayerCapacity() {
        return this.getServer().getMaxPlayers();
    }

    @Override
    public ScheduledTask scheduleTask(Runnable task, long delay, long period) {
        var bukkitTask = this.getServer().getScheduler().runTaskTimer(this, task, 20 * delay, 20 * period);
        return bukkitTask::cancel;
    }

    @Override
    public ScheduledTask scheduleTask(Runnable task, long delay) {
        var bukkitTask = this.getServer().getScheduler().runTaskLater(this, task, 20 * delay);
        return bukkitTask::cancel;
    }

    @Override
    public RegisteredHook addHook(PlayerConnectHook hook) {
        return this.registerEvent(PlayerLoginEvent.class, event -> {
            Component component = hook.onPlayerConnecting(event.getPlayer().getUniqueId());

            if (component == null) {
                return;
            }

            if (ADVENTURE_TEXT_SUPPORTED) {
                event.disallow(Result.KICK_OTHER, component);
            } else {
                event.disallow(
                        Result.KICK_OTHER,
                        LegacyComponentSerializer.legacySection()
                                .serialize(component)
                );
            }
        });
    }

    @Override
    public RegisteredHook addHook(PostPlayerConnectHook hook) {
        return this.registerEvent(PlayerJoinEvent.class, event -> {
            hook.onPlayerConnected(event.getPlayer().getUniqueId());
        });
    }

    @Override
    public RegisteredHook addHook(PlayerDisconnectHook hook) {
        return this.registerEvent(PlayerQuitEvent.class, event -> {
            hook.onPlayerDisconnect(event.getPlayer().getUniqueId());
        });
    }

    private <E> RegisteredHook registerEvent(Class<E> eventClass, Consumer<E> consumer) {
        Listener listener = new Listener() {

            @EventHandler
            private void handle(E event) {
                consumer.accept(event);
            }

        };
        Bukkit.getPluginManager().registerEvents(listener, this);
        return () -> HandlerList.unregisterAll(listener);
    }

}
