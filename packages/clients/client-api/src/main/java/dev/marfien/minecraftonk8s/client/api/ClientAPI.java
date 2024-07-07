package dev.marfien.minecraftonk8s.client.api;

public interface ClientAPI {

    void allocate();
    void reserve(int seconds);
    void requestShutdown();

    String getName();
    String getNamespace();

}
