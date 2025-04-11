package dev.marfien.minecraftonk8s.operator.service;

import dev.marfien.minecraftonk8s.agones.model.Fleet;
import dev.marfien.minecraftonk8s.agones.model.GameServerTemplateSpec;
import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.MinecraftProxyFleet;
import dev.marfien.minecraftonk8s.operator.application.BinariesConfigMapEnforcer;
import dev.marfien.minecraftonk8s.operator.util.CrdUtils;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.IntOrString;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class MinecraftProxyFleetServiceImpl implements MinecraftProxyFleetService {

    @Inject
    BinariesConfigMapEnforcer binariesConfigMapEnforcer;

    @Inject
    ConditionService conditionService;

    @Override
    public MinecraftProxyFleet patchStatus(MinecraftProxyFleet fleet, Fleet backedFleet) {
        this.binariesConfigMapEnforcer.ensureAgentBinary();

        IntOrString serviceTargetPort = fleet.getSpec().getService().getTargetPort();

        // Check if the target port has a matching container port
        GameServerTemplateSpec gsSpec = backedFleet.getSpec().getTemplate();
        List<Container> containers = gsSpec.getSpec().getTemplate().getSpec().getContainers();

        if (containers.stream()
                .flatMap(c -> c.getPorts().stream())
                .noneMatch(port -> CrdUtils.containerPortEquals(port, serviceTargetPort))) {
            throw new IllegalArgumentException("The target port does not match any container port.");
        }

        return fleet.edit()
                .editStatus()
                    .withReplicas(backedFleet.getStatus().getReplicas())
                    .withReadyReplicas(backedFleet.getStatus().getReadyReplicas())
                    .withAllocatedReplicas(backedFleet.getStatus().getAllocatedReplicas())
                    .addToConditions(this.conditionService.ready("FleetReconciled", "The backing agones fleet has been reconciled successfully."))
                    .endStatus()
                .build();
    }

    @Override
    public MinecraftProxyFleet patchErrorStatus(MinecraftProxyFleet resource, Exception e) {
        return resource.edit()
                .editStatus()
                    .addToConditions(this.conditionService.fromException(e))
                    .endStatus()
                .build();
    }
}
