package dev.marfien.agones.kubernetes.api.model.autoscaling;

import io.fabric8.kubernetes.api.model.IntOrString;

public class BufferPolicy {

  private int maxReplicas;

  private int minReplicas;

  private IntOrString bufferSize;

  public int getMaxReplicas() {
    return this.maxReplicas;
  }

  public void setMaxReplicas(int maxReplicas) {
    this.maxReplicas = maxReplicas;
  }

  public int getMinReplicas() {
    return this.minReplicas;
  }

  public void setMinReplicas(int minReplicas) {
    this.minReplicas = minReplicas;
  }

  public IntOrString getBufferSize() {
    return this.bufferSize;
  }

  public void setBufferSize(IntOrString bufferSize) {
    this.bufferSize = bufferSize;
  }
}
