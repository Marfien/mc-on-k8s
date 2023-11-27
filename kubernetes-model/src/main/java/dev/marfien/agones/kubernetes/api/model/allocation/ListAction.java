package dev.marfien.agones.kubernetes.api.model.allocation;

import java.util.List;

public class ListAction {

    private List<String> addValues;

    private long capacity;

    public List<String> getAddValues() {
        return this.addValues;
    }

    public void setAddValues(List<String> addValues) {
        this.addValues = addValues;
    }

    public long getCapacity() {
        return this.capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }
}
