package dev.marfien.minecraftonk8s.operator.minecraftserverfleet;

import dev.marfien.minecraftonk8s.api.model.Fleet;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerFleet;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerFleetBuilder;
import io.javaoperatorsdk.operator.api.reconciler.Cleaner;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.DeleteControl;
import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusHandler;
import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusUpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;

public class MinecraftServerFleetReconciler implements Reconciler<MinecraftServerFleet>,
        ErrorStatusHandler<MinecraftServerFleet>, Cleaner<MinecraftServerFleet> {

    public static final String SELECTOR = "mconk8s.marfien.dev/managed-by-mcsf-reconciler";

    @Override
    public UpdateControl<MinecraftServerFleet> reconcile(MinecraftServerFleet resource,
            Context<MinecraftServerFleet> context) throws Exception {

        Fleet dependent = context.getSecondaryResource(Fleet.class).orElseThrow();
        MinecraftServerFleet updated = new MinecraftServerFleetBuilder(resource)
                .withNewStatus()
                .withReplicas(dependent.getStatus().getReplicas())
                    .withReadyReplicas(dependent.getStatus().getReadyReplicas())
                    .withAllocatedReplicas(dependent.getStatus().getAllocatedReplicas())
                    .endStatus()
                .build();

        return UpdateControl.patchStatus(updated);
    }

    @Override
    public ErrorStatusUpdateControl<MinecraftServerFleet> updateErrorStatus(
            MinecraftServerFleet resource, Context<MinecraftServerFleet> context, Exception e) {
        resource.getStatus().setErrorMessage(e.getMessage());
        return ErrorStatusUpdateControl.patchStatus(resource);
    }

    @Override
    public DeleteControl cleanup(MinecraftServerFleet resource,
            Context<MinecraftServerFleet> context) {
        return DeleteControl.defaultDelete();
    }

}
