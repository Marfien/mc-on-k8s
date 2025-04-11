package dev.marfien.minecraftonk8s.operator.service;

import dev.marfien.minecraftonk8s.agones.model.Fleet;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerFleet;
import dev.marfien.minecraftonk8s.operator.application.BinariesConfigMapEnforcer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MinecraftServerFleetServiceImpl implements MinecraftServerFleetService {

    @Inject
    BinariesConfigMapEnforcer binariesConfigMapEnforcer;

    @Inject
    ConditionService conditionService;

    @Override
    public MinecraftServerFleet patch(MinecraftServerFleet fleet, Fleet backedFleet) {
        this.binariesConfigMapEnforcer.ensureAgentBinary();

        return fleet.edit()
                .editStatus()
                    .withReplicas(backedFleet.getStatus().getReplicas())
                    .withReadyReplicas(backedFleet.getStatus().getReadyReplicas())
                    .withAllocatedReplicas(backedFleet.getStatus().getAllocatedReplicas())
                    .addToConditions(this.conditionService.ready("Reconciled", "FleetReconciled", "The backing agones fleet has been reconciled successfully."))
                    .endStatus()
                .build();
    }

    @Override
    public MinecraftServerFleet patchErrorStatus(MinecraftServerFleet resource, Exception e) {
        return resource.edit()
                .editStatus()
                    .addToConditions(this.conditionService.fromException(e))
                    .endStatus()
                .build();
    }
}
