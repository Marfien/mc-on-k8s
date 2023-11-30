package dev.marfien.agones.kubernetes.api.model.autoscaling;

import io.fabric8.kubernetes.api.model.IntOrString;

public class ListPolicy {

  private String key;

  private long maxCapacity;

  private long minCapacity;

  private IntOrString bufferSize;

  public String getKey() {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public long getMaxCapacity() {
    return this.maxCapacity;
  }

  public void setMaxCapacity(long maxCapacity) {
    this.maxCapacity = maxCapacity;
  }

  public long getMinCapacity() {
    return this.minCapacity;
  }

  public void setMinCapacity(long minCapacity) {
    this.minCapacity = minCapacity;
  }

  public IntOrString getBufferSize() {
    return this.bufferSize;
  }

  public void setBufferSize(IntOrString bufferSize) {
    this.bufferSize = bufferSize;
  }
}
