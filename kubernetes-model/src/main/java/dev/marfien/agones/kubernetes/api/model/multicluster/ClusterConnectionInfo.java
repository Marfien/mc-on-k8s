package dev.marfien.agones.kubernetes.api.model.multicluster;

import java.util.List;

public class ClusterConnectionInfo {

  private String clusterName;

  private List<String> allocationEndpoints;

  private String secretName;

  private String namespace;

  private byte[] serverCa;

  public String getClusterName() {
    return this.clusterName;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }

  public List<String> getAllocationEndpoints() {
    return this.allocationEndpoints;
  }

  public void setAllocationEndpoints(List<String> allocationEndpoints) {
    this.allocationEndpoints = allocationEndpoints;
  }

  public String getSecretName() {
    return this.secretName;
  }

  public void setSecretName(String secretName) {
    this.secretName = secretName;
  }

  public String getNamespace() {
    return this.namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public byte[] getServerCa() {
    return this.serverCa;
  }

  public void setServerCa(byte[] serverCa) {
    this.serverCa = serverCa;
  }
}
