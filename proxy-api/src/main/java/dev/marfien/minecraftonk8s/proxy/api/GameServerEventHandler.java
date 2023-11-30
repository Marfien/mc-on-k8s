package dev.marfien.minecraftonk8s.proxy.api;

import dev.marfien.agones.kubernetes.api.model.GameServer;
import dev.marfien.agones.kubernetes.api.model.GameServerStatus;
import dev.marfien.agones.kubernetes.api.model.GameServerStatusPort;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import java.net.InetSocketAddress;
import java.util.List;

public class GameServerEventHandler implements ResourceEventHandler<GameServer> {

  private final ProxyAgonesBridge connector;

  public GameServerEventHandler(ProxyAgonesBridge connector) {
    this.connector = connector;
  }

  @Override
  public void onAdd(GameServer obj) {
    // Nothing to do here
    // We cannot use any gameserver not being ready
  }

  @Override
  public void onUpdate(GameServer oldObj, GameServer newObj) {
    GameServerStatus status = newObj.getStatus();
    if ("Ready".equals(oldObj.getStatus().getState()) || !"Ready".equals(status.getState())) return;

    String name = newObj.getMetadata().getName();
    String hostname = status.getAddress();
    int port = findPort(status.getPorts());

    this.connector.addServer(name, new InetSocketAddress(hostname, port));
  }

  @Override
  public void onDelete(GameServer obj, boolean deletedFinalStateUnknown) {
    String name = obj.getMetadata().getName();
    GameServerStatus status = obj.getStatus();
    String hostname = status.getAddress();
    int port = findPort(status.getPorts());

    this.connector.removeServer(name, new InetSocketAddress(hostname, port));
  }

  private int findPort(List<GameServerStatusPort> ports) {
    for (GameServerStatusPort port : ports) {
      if ("minecraft".equals(port.getName())) return port.getPort();
    }

    throw new IllegalStateException("There is no suitable port present: " + ports);
  }
}
