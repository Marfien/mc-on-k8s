package dev.marfien.agones.kubernetes.api.model.multicluster;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Kind;
import io.fabric8.kubernetes.model.annotation.Version;

@Version("v1")
@Group("multicluster.agones.dev")
@Kind("GameServerAllocationPolicy")
public class GameServerAllocationPolicy
    extends CustomResource<GameServerAllocationPolicySpec, GameServerAllocationPolicyStatus>
    implements Namespaced {}
