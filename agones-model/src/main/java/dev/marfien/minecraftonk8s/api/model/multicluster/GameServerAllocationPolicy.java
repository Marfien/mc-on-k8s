package dev.marfien.minecraftonk8s.api.model.multicluster;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Kind;
import io.fabric8.kubernetes.model.annotation.Version;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;

@Version("v1")
@Group("multicluster.agones.dev")
@Kind("GameServerAllocationPolicy")
@Buildable(editableEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder",
        lazyCollectionInitEnabled = false, refs = {
        @BuildableReference(io.fabric8.kubernetes.api.model.ObjectMeta.class),
        @BuildableReference(CustomResource.class),
})
public class GameServerAllocationPolicy
        extends CustomResource<GameServerAllocationPolicySpec, GameServerAllocationPolicyStatus>
        implements Namespaced {

}
