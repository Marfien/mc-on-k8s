package dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet;

import dev.marfien.minecraftonk8s.api.model.FleetAutoScalingSpec;
import io.fabric8.kubernetes.api.builder.Editable;
import io.fabric8.kubernetes.api.model.apps.DeploymentStrategy;
import io.sundr.builder.annotations.Buildable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class MinecraftProxyFleetSpec implements Editable<MinecraftProxyFleetSpecBuilder> {

    private int replicas;
    private MinecraftProxySpec template;
    private FleetAutoScalingSpec autoScaling;
    private DeploymentStrategy deploymentStrategy;
    private MinecraftProxyFleetServiceType serviceType;

    @Override
    public MinecraftProxyFleetSpecBuilder edit() {
        return new MinecraftProxyFleetSpecBuilder(this);
    }
}
