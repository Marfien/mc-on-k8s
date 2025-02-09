package dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import dev.marfien.minecraftonk8s.api.model.FleetAutoScalingSpec;
import io.fabric8.generator.annotation.Default;
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

    @Default("2")
    @JsonPropertyDescription("The number of replicas to run.")
    private int replicas = 2;

    @JsonPropertyDescription("The template for the replicated MinecraftProxy.")
    private MinecraftProxySpec template;

    @JsonPropertyDescription("The auto-scaling configuration for the fleet. See Agones documentation for more information.")
    private FleetAutoScalingSpec autoScaling;

    @JsonPropertyDescription("The deployment strategy for the fleet. See Kubernetes documentation for more information.")
    private DeploymentStrategy deploymentStrategy;

    @Default("LOAD_BALANCER")
    @JsonPropertyDescription("The service type for the fleet. See Kubernetes documentation for more information.")
    private MinecraftProxyFleetServiceType serviceType = MinecraftProxyFleetServiceType.LOAD_BALANCER;

    @JsonPropertyDescription("The drainage configuration for all the proxies in this fleet.")
    private DrainageSpec drainage;

    @Override
    public MinecraftProxyFleetSpecBuilder edit() {
        return new MinecraftProxyFleetSpecBuilder(this);
    }
}
