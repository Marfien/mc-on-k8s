package dev.marfien.minecraftonk8s.api.model.minecraftserverfleet;

public class MinecraftServerFleetStatus {

    private int replicas;
    private int readyReplicas;
    private int allocatedReplicas;

    public int getReplicas() {
        return replicas;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }

    public int getReadyReplicas() {
        return readyReplicas;
    }

    public void setReadyReplicas(int readyReplicas) {
        this.readyReplicas = readyReplicas;
    }

    public int getAllocatedReplicas() {
        return allocatedReplicas;
    }

    public void setAllocatedReplicas(int allocatedReplicas) {
        this.allocatedReplicas = allocatedReplicas;
    }
}
