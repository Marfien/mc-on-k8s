package dev.marfien.minecraftonk8s.agones.model;

import io.fabric8.kubernetes.api.model.apps.DeploymentStrategy;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder", refs = {
        @BuildableReference(DeploymentStrategy.class)
})
public class FleetSpec {

    private int replicas;

    private AllocationOverflow allocationOverflow;

    private DeploymentStrategy deploymentStrategy;

    private String scheduling;

    private List<Priority> priorities;

    private GameServerTemplateSpec template;

}
