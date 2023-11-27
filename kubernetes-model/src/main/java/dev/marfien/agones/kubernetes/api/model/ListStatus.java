package dev.marfien.agones.kubernetes.api.model;

import java.util.List;

public class ListStatus {

    private long capacity;

    private List<String> values;

    public long getCapacity() {
        return this.capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public List<String> getValues() {
        return this.values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
