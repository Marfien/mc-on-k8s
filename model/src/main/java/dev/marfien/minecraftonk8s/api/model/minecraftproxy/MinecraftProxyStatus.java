package dev.marfien.minecraftonk8s.api.model.minecraftproxy;

public class MinecraftProxyStatus {

    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        STARTING,
        ACTIVE,
        DRAINING,
        STOPPING
    }

}
