package dev.marfien.agones.kubernetes.api.model;

public class SdkServer {

  private String logLevel;

  private int grpcPort;

  private int httpPort;

  public String getLogLevel() {
    return this.logLevel;
  }

  public void setLogLevel(String logLevel) {
    this.logLevel = logLevel;
  }

  public int getGrpcPort() {
    return this.grpcPort;
  }

  public void setGrpcPort(int grpcPort) {
    this.grpcPort = grpcPort;
  }

  public int getHttpPort() {
    return this.httpPort;
  }

  public void setHttpPort(int httpPort) {
    this.httpPort = httpPort;
  }
}
