package dev.marfien.agones.kubernetes.api.model.allocation;

public class CounterSelector {

  private long minCount;

  private long maxCount;

  private long minAvailable;

  private long maxAvailable;

  public long getMinCount() {
    return this.minCount;
  }

  public void setMinCount(long minCount) {
    this.minCount = minCount;
  }

  public long getMaxCount() {
    return this.maxCount;
  }

  public void setMaxCount(long maxCount) {
    this.maxCount = maxCount;
  }

  public long getMinAvailable() {
    return this.minAvailable;
  }

  public void setMinAvailable(long minAvailable) {
    this.minAvailable = minAvailable;
  }

  public long getMaxAvailable() {
    return this.maxAvailable;
  }

  public void setMaxAvailable(long maxAvailable) {
    this.maxAvailable = maxAvailable;
  }
}
