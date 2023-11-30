package dev.marfien.agones.kubernetes.api.model;

public class AggregatedListStatus {

  private long allocatedCount;

  private long allocatedCapacity;

  private long count;

  private long capacity;

  public long getAllocatedCount() {
    return this.allocatedCount;
  }

  public void setAllocatedCount(long allocatedCount) {
    this.allocatedCount = allocatedCount;
  }

  public long getAllocatedCapacity() {
    return this.allocatedCapacity;
  }

  public void setAllocatedCapacity(long allocatedCapacity) {
    this.allocatedCapacity = allocatedCapacity;
  }

  public long getCount() {
    return this.count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  public long getCapacity() {
    return this.capacity;
  }

  public void setCapacity(long capacity) {
    this.capacity = capacity;
  }
}
