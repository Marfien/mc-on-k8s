package dev.marfien.minecraftonk8s.api.model;

import dev.marfien.minecraftonk8s.api.model.autoscaling.FleetAutoscalerPolicy;

public class FleetAutoScalingSpec {

    private FleetAutoscalerPolicy policy;

    public FleetAutoscalerPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(FleetAutoscalerPolicy policy) {
        this.policy = policy;
    }
}
