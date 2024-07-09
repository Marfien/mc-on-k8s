package dev.marfien.minecraftonk8s.operator.minecraftproxyfleet;

import dev.marfien.minecraftonk8s.agones.model.Fleet;
import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.MinecraftProxyFleet;
import dev.marfien.minecraftonk8s.operator.minecraftproxyfleet.dependentresource.FleetDependentResource;
import dev.marfien.minecraftonk8s.operator.minecraftproxyfleet.dependentresource.ServiceDependentResource;
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
                @Dependent(type = FleetDependentResource.class),
                @Dependent(type = ServiceDependentResource.class)
        }
)
public class MinecraftProxyFleetReconciler implements Reconciler<MinecraftProxyFleet>,
        ErrorStatusHandler<MinecraftProxyFleet>, Cleaner<MinecraftProxyFleet> {

    public static final String SELECTOR = "mconk8s.marfien.dev/controlled-by=proxy-fleet-reconciler";

    @Override
    public UpdateControl<MinecraftProxyFleet> reconcile(MinecraftProxyFleet resource,
            Context<MinecraftProxyFleet> context) throws Exception {
        Fleet proxyFleet = context.getSecondaryResource(Fleet.class).orElseThrow();

        return UpdateControl.patchStatus(
                resource.edit()
                        .editStatus()
                            .withReplicas(proxyFleet.getStatus().getReplicas())
                            .withReadyReplicas(proxyFleet.getStatus().getReadyReplicas())
                            .withAllocatedReplicas(proxyFleet.getStatus().getAllocatedReplicas())
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
    public ErrorStatusUpdateControl<MinecraftProxyFleet> updateErrorStatus(
            MinecraftProxyFleet resource, Context<MinecraftProxyFleet> context, Exception e) {
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
                        .build());
    }

    @Override
    public DeleteControl cleanup(MinecraftProxyFleet resource,
            Context<MinecraftProxyFleet> context) {
        return DeleteControl.defaultDelete();
    }
}
