package dev.marfien.minecraftonk8s.client.common;

import dev.marfien.minecraftonk8s.client.common.hook.PlayerConnectionHook;

public interface ClientInterface {

    int getPlayerCount();
    int getPlayerCapacity();

    ScheduledTask scheduleTask(Runnable task, long delay, long period);
    ScheduledTask scheduleTask(Runnable task, long delay);

    RegisteredHook addHook(PlayerConnectionHook hook);

    interface RegisteredHook {
        void unregister();
    }

    interface ScheduledTask {
        void cancel();
    }

}
