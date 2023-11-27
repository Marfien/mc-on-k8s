package dev.marfien.agones.kubernetes.api.model.allocation;

public class ListSelector {

    private String containsValue;

    private long minAvailable;

    private long maxAvailable;

    public String getContainsValue() {
        return this.containsValue;
    }

    public void setContainsValue(String containsValue) {
        this.containsValue = containsValue;
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
