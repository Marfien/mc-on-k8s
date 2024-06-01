package dev.marfien.minecraftonk8s.operator.minecraftserverfleet;

import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerFleet;
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
        return null;
    }

    @Override
    public ErrorStatusUpdateControl<MinecraftServerFleet> updateErrorStatus(
            MinecraftServerFleet resource, Context<MinecraftServerFleet> context, Exception e) {
        resource.getStatus().setErrorMessage(e.getMessage());
        return ErrorStatusUpdateControl.updateStatus(resource);
    }

    @Override
    public DeleteControl cleanup(MinecraftServerFleet resource,
            Context<MinecraftServerFleet> context) {
        return DeleteControl.defaultDelete();
    }

}
