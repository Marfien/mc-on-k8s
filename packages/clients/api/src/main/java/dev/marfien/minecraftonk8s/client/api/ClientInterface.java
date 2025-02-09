package dev.marfien.minecraftonk8s.client.api;

import dev.marfien.minecraftonk8s.client.api.hook.PlayerConnectHook;
import dev.marfien.minecraftonk8s.client.api.hook.PlayerDisconnectHook;
import dev.marfien.minecraftonk8s.client.api.hook.PostPlayerConnectHook;

public interface ClientInterface {

    int getPlayerCount();
    int getPlayerCapacity();

    ScheduledTask scheduleTask(Runnable task, long delay, long period);
    ScheduledTask scheduleTask(Runnable task, long delay);

    RegisteredHook addHook(PlayerConnectHook hook);
    RegisteredHook addHook(PostPlayerConnectHook hook);
    RegisteredHook addHook(PlayerDisconnectHook hook);

    interface RegisteredHook {
        void unregister();
    }

    interface ScheduledTask {
        void cancel();
    }

}
