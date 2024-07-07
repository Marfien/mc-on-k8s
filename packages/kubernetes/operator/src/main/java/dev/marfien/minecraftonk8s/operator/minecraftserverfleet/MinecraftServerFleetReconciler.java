package dev.marfien.minecraftonk8s.operator.minecraftserverfleet;

import dev.marfien.minecraftonk8s.agones.model.Fleet;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerFleet;
import dev.marfien.minecraftonk8s.operator.minecraftserverfleet.dependentresource.FleetDependentResource;
import io.javaoperatorsdk.operator.api.reconciler.Cleaner;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.DeleteControl;
import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusHandler;
import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusUpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;

@ControllerConfiguration(
        dependents = {
                @Dependent(type = FleetDependentResource.class)
        }
)
public class MinecraftServerFleetReconciler implements Reconciler<MinecraftServerFleet>,
        ErrorStatusHandler<MinecraftServerFleet>, Cleaner<MinecraftServerFleet> {

    public static final String SELECTOR = "mconk8s.marfien.dev/controlled-by==fleet-reconciler";

    @Override
    public UpdateControl<MinecraftServerFleet> reconcile(
            MinecraftServerFleet resource, Context<MinecraftServerFleet> context) throws Exception {
        Fleet dependent = context.getSecondaryResource(Fleet.class).orElseThrow();

        return UpdateControl.patchStatus(
                resource.edit()
                        .editStatus()
                            .withReplicas(dependent.getStatus().getReplicas())
                            .withReadyReplicas(dependent.getStatus().getReadyReplicas())
                            .withAllocatedReplicas(dependent.getStatus().getAllocatedReplicas())
                            .addNewCondition()
                                .withStatus("True")
                                .withType("Reconciled")
                                .withReason("FleetReconciled")
                                .withMessage("The fleet has been reconciled successfully.")
                            .endCondition()
                        .endStatus()
                        .build()
        );
    }

    @Override
    public ErrorStatusUpdateControl<MinecraftServerFleet> updateErrorStatus(
            MinecraftServerFleet resource, Context<MinecraftServerFleet> context, Exception e) {
        return ErrorStatusUpdateControl.patchStatus(
                resource.edit()
                    .editStatus()
                        .addNewCondition()
                            .withStatus("False")
                            .withType("Error")
                            .withReason("ReconcileError")
                            .withMessage(e.getMessage())
                        .endCondition()
                    .endStatus()
                .build()
        );
    }

    @Override
    public DeleteControl cleanup(MinecraftServerFleet resource,
            Context<MinecraftServerFleet> context) {
        return DeleteControl.defaultDelete();
    }

}
