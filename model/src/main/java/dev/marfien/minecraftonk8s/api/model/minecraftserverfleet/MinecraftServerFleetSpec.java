package dev.marfien.minecraftonk8s.api.model.minecraftserverfleet;

import dev.marfien.minecraftonk8s.api.model.FleetAutoScalingSpec;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServerSpec;

public class MinecraftServerFleetSpec {

    private int targetReplicas;
    private MinecraftServerSpec template;
    private FleetAutoScalingSpec autoScaling;

    public int getTargetReplicas() {
        return targetReplicas;
    }

    public void setTargetReplicas(int targetReplicas) {
        this.targetReplicas = targetReplicas;
    }

    public MinecraftServerSpec getTemplate() {
        return template;
    }

    public void setTemplate(MinecraftServerSpec template) {
        this.template = template;
    }

    public FleetAutoScalingSpec getAutoScaling() {
        return autoScaling;
    }

    public void setAutoScaling(FleetAutoScalingSpec autoScaling) {
        this.autoScaling = autoScaling;
    }
}
