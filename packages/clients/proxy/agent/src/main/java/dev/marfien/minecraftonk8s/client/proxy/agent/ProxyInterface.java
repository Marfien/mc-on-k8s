package dev.marfien.minecraftonk8s.client.proxy.agent;

import dev.marfien.minecraftonk8s.client.api.ClientInterface;
import net.kyori.adventure.text.Component;

public interface ProxyInterface extends ClientInterface {

    void addServer(String serverName, String address, int port);
    void removeServer(String serverName);
    boolean hasServer(String serverName);

    void kickAll(Component s);
}
