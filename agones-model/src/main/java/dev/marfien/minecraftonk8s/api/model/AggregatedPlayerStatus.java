package dev.marfien.minecraftonk8s.api.model;

public class AggregatedPlayerStatus {

  private long count;

  private long capacity;

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
