package dev.marfien.minecraftonk8s.proxy.shared;

public interface ProxyConnector {

    void addServer(String serverName, String address, int port);
    void removeServer(String serverName);
    boolean hasServer(String serverName);

    int getPlayerCount();
    int getPlayerCapacity();

    ScheduledTask scheduleTask(Runnable task, long delay, long period);
    ScheduledTask scheduleTask(Runnable task, long delay);

    interface ScheduledTask {
        void cancel();
    }

}
