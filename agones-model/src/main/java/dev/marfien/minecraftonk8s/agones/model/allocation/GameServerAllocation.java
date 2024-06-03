package dev.marfien.minecraftonk8s.agones.model.allocation;

import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Kind;
import io.fabric8.kubernetes.model.annotation.Version;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;

@Version("v1")
@Group("allocation.agones.dev")
@Kind("GameServerAllocation")
@Buildable(editableEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder",
        lazyCollectionInitEnabled = false, refs = {
        @BuildableReference(io.fabric8.kubernetes.api.model.ObjectMeta.class),
        @BuildableReference(CustomResource.class),
})
public class GameServerAllocation
        extends CustomResource<GameServerAllocationSpec, GameServerAllocationStatus> {

}
