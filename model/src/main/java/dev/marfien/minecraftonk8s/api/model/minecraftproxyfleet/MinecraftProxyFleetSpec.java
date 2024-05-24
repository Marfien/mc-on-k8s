package dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet;

import dev.marfien.minecraftonk8s.api.model.FleetAutoScalingSpec;
import dev.marfien.minecraftonk8s.api.model.minecraftproxy.MinecraftProxySpec;

public class MinecraftProxyFleetSpec {

    private int targetReplicas;
    private MinecraftProxySpec template;
    private FleetAutoScalingSpec autoScaling;

    public int getTargetReplicas() {
        return targetReplicas;
    }

    public void setTargetReplicas(int targetReplicas) {
        this.targetReplicas = targetReplicas;
    }

    public MinecraftProxySpec getTemplate() {
        return template;
    }

    public void setTemplate(MinecraftProxySpec template) {
        this.template = template;
    }

    public FleetAutoScalingSpec getAutoScaling() {
        return autoScaling;
    }

    public void setAutoScaling(FleetAutoScalingSpec autoScaling) {
        this.autoScaling = autoScaling;
    }
}
