package dev.marfien.agones.kubernetes.api.model.multicluster;

public class GameServerAllocationPolicySpec {

  private int priority;

  private int weight;

  private ClusterConnectionInfo connectionInfo;

  public int getPriority() {
    return this.priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public int getWeight() {
    return this.weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public ClusterConnectionInfo getConnectionInfo() {
    return this.connectionInfo;
  }

  public void setConnectionInfo(ClusterConnectionInfo connectionInfo) {
    this.connectionInfo = connectionInfo;
  }
}
