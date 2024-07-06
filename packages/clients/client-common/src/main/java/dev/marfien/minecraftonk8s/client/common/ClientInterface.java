package dev.marfien.minecraftonk8s.client.common;

public interface ClientInterface {

    ScheduledTask scheduleTask(Runnable task, long delay, long period);
    ScheduledTask scheduleTask(Runnable task, long delay);

    interface ScheduledTask {
        void cancel();
    }

}
