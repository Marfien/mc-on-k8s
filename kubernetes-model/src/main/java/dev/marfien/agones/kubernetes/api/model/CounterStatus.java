package dev.marfien.agones.kubernetes.api.model;

public class CounterStatus {

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
