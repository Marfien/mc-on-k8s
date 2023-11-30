package dev.marfien.agones.kubernetes.api.model.allocation;

import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Kind;
import io.fabric8.kubernetes.model.annotation.Version;

@Version("v1")
@Group("allocation.agones.dev")
@Kind("GameServerAllocation")
public class GameServerAllocation
    extends CustomResource<GameServerAllocationSpec, GameServerAllocationStatus> {}
