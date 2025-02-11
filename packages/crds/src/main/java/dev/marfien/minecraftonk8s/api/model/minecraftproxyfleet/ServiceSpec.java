package dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.fabric8.crd.generator.annotation.SelectableField;
import io.fabric8.generator.annotation.Default;
import io.fabric8.generator.annotation.Required;
import io.fabric8.kubernetes.api.builder.Editable;
import io.fabric8.kubernetes.api.model.IntOrString;
import io.sundr.builder.annotations.Buildable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class ServiceSpec implements Editable<ServiceSpecBuilder> {

    @SelectableField
    @Default("LOAD_BALANCER")
    @JsonPropertyDescription("The service type for the fleet. See Kubernetes documentation for more information.")
    private MinecraftProxyFleetServiceType type = MinecraftProxyFleetServiceType.LOAD_BALANCER;

    @Required
    @JsonPropertyDescription("The port to expose on the service.")
    private Integer port;

    // TODO error
    @Required
    @JsonPropertyDescription("The target port for the service. Must match with a port configured in the MinecraftProxySpec.")
    private IntOrString targetPort;

    @SelectableField
    @Default("TCP")
    @JsonPropertyDescription("The protocol for the service.")
    private String protocol = "TCP";

    @Override
    public ServiceSpecBuilder edit() {
        return new ServiceSpecBuilder(this);
    }

}
