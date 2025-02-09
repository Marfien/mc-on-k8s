package dev.marfien.minecraftonk8s.agones.model.multicluster;

import io.sundr.builder.annotations.Buildable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class GameServerAllocationPolicySpec {

    private int priority;

    private int weight;

    private ClusterConnectionInfo connectionInfo;

}
