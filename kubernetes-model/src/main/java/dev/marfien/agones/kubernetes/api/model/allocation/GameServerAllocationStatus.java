package dev.marfien.agones.kubernetes.api.model.allocation;

import dev.marfien.agones.kubernetes.api.model.GameServerStatusPort;
import io.fabric8.kubernetes.api.model.NodeAddress;
import java.util.List;

public class GameServerAllocationStatus {

  private String state;

  private String gameServerName;

  private List<GameServerStatusPort> ports;

  private String address;

  private List<NodeAddress> addresses;

  private String nodeName;

  private String source;

  private GameServerMetadata metadata;

  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getGameServerName() {
    return this.gameServerName;
  }

  public void setGameServerName(String gameServerName) {
    this.gameServerName = gameServerName;
  }

  public List<GameServerStatusPort> getPorts() {
    return this.ports;
  }

  public void setPorts(List<GameServerStatusPort> ports) {
    this.ports = ports;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public List<NodeAddress> getAddresses() {
    return this.addresses;
  }

  public void setAddresses(List<NodeAddress> addresses) {
    this.addresses = addresses;
  }

  public String getNodeName() {
    return this.nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public String getSource() {
    return this.source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public GameServerMetadata getMetadata() {
    return this.metadata;
  }

  public void setMetadata(GameServerMetadata metadata) {
    this.metadata = metadata;
  }
}
