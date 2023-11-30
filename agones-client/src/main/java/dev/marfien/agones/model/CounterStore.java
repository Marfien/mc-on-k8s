package dev.marfien.agones.model;

import dev.agones.sdk.alpha.Counter;

public record CounterStore(String name, long count, long capacity) {

  public CounterStore(Counter counter) {
    this(counter.getName(), counter.getCount(), counter.getCapacity());
  }

  public Counter toAgonesCounter() {
    return Counter.newBuilder()
        .setName(this.name)
        .setCount(this.count)
        .setCapacity(this.capacity)
        .build();
  }
}
