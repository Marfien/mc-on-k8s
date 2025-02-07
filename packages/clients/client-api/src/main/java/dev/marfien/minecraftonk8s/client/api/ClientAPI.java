package dev.marfien.minecraftonk8s.client.api;

import java.time.Duration;

public interface ClientAPI {

    void allocate();
    void reserve(Duration duration);
    void ready();

    void requestShutdown();

    static ClientAPI get() {
        return InstanceHolder.instance;
    }

    static void set(ClientAPI instance) {
        if (InstanceHolder.instance != null) {
            throw new IllegalStateException("ClientAPI instance already set");
        }
        InstanceHolder.instance = instance;
    }

    class InstanceHolder {
        private static ClientAPI instance;
    }

}
