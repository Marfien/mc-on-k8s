package dev.marfien.minecraftonk8s.api.model;

import io.fabric8.kubernetes.api.model.apps.DeploymentStrategy;
import java.util.List;
import io.sundr.builder.annotations.BuildableReference;
import lombok.AllArgsConstructor;
import io.sundr.builder.annotations.Buildable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder", refs = {
        @BuildableReference(DeploymentStrategy.class)
})
@NoArgsConstructor
@AllArgsConstructor
public class FleetSpec {

    private int replicas;

    private AllocationOverflow allocationOverflow;

    private DeploymentStrategy deploymentStrategy;

    private String scheduling;

    private List<Priority> priorities;

    private GameServerTemplateSpec template;

}
