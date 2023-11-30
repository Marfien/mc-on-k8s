package dev.marfien.agones.kubernetes.api.model;

public class PlayerSpec {

  private long initialCapacity;

  public long getInitialCapacity() {
    return this.initialCapacity;
  }

  public void setInitialCapacity(long initialCapacity) {
    this.initialCapacity = initialCapacity;
  }
}
