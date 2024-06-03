package dev.marfien.minecraftonk8s.api.model.minecraftserverfleet;

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
public class MinecraftServerFleetSpec implements Editable<MinecraftServerFleetSpecBuilder> {

    private int targetReplicas;
    private MinecraftServerTemplateSpec template;
    private FleetAutoScalingSpec autoScaling;
    private DeploymentStrategy deploymentStrategy;

    @Override
    public MinecraftServerFleetSpecBuilder edit() {
        return new MinecraftServerFleetSpecBuilder(this);
    }
}
