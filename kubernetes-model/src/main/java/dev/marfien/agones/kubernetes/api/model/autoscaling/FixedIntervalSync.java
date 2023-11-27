package dev.marfien.agones.kubernetes.api.model.autoscaling;

public class FixedIntervalSync {

    private int seconds;

    public int getSeconds() {
        return this.seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
