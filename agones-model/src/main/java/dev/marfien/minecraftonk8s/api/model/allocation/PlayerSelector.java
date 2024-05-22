package dev.marfien.minecraftonk8s.api.model.allocation;

public class PlayerSelector {

  private long minAvailable;

  private long maxAvailable;

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
