package dev.marfien.minecraftonk8s.api.model;

public class GameServerStatusPort {

  private String name;

  private int port;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPort() {
    return this.port;
  }

  public void setPort(int port) {
    this.port = port;
  }
}
