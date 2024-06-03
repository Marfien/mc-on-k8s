package dev.marfien.minecraftonk8s.agones.model.multicluster;

import io.sundr.builder.annotations.Buildable;
import lombok.Data;

@Data
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class GameServerAllocationPolicyStatus {

}
