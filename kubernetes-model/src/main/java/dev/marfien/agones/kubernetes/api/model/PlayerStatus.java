package dev.marfien.agones.kubernetes.api.model;

import java.util.List;

public class PlayerStatus {

  private long count;

  private long capacity;

  private List<String> ids;

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

  public List<String> getIds() {
    return this.ids;
  }

  public void setIds(List<String> ids) {
    this.ids = ids;
  }
}
