package dev.marfien.minecraftonk8s.api.model.minecraftserverfleet;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import dev.marfien.minecraftonk8s.api.model.FleetAutoScalingSpec;
import io.fabric8.generator.annotation.Default;
import io.fabric8.generator.annotation.Min;
import io.fabric8.generator.annotation.Required;
import io.fabric8.kubernetes.api.builder.Editable;
import io.fabric8.kubernetes.api.model.apps.DeploymentStrategy;
import io.fabric8.kubernetes.model.annotation.SpecReplicas;
import io.sundr.builder.annotations.Buildable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class MinecraftServerFleetSpec implements Editable<MinecraftServerFleetSpecBuilder> {

    @Min(0)
    @Default("2")
    @SpecReplicas
    @JsonPropertyDescription("The number of replicas to run for this fleet.")
    private int targetReplicas;

    @Required
    @JsonPropertyDescription("The template for the fleet.")
    private MinecraftServerSpecTemplate template;

    @JsonPropertyDescription("The auto-scaling configuration for the fleet.")
    private FleetAutoScalingSpec autoScaling;

    @JsonPropertyDescription("The deployment strategy for the fleet. See https://kubernetes.io/docs/concepts/workloads/controllers/deployment/#strategy for more information.")
    private DeploymentStrategy deploymentStrategy;

    @Override
    public MinecraftServerFleetSpecBuilder edit() {
        return new MinecraftServerFleetSpecBuilder(this);
    }
}
