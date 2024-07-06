package dev.marfien.minecraftonk8s.client.proxy.agent;

import dev.marfien.minecraftonk8s.client.common.ClientInterface;

public interface ProxyInterface extends ClientInterface {

    void addServer(String serverName, String address, int port);
    void removeServer(String serverName);
    boolean hasServer(String serverName);

}
