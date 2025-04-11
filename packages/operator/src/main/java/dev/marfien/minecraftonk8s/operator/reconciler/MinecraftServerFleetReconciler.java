package dev.marfien.minecraftonk8s.operator.reconciler;

import dev.marfien.minecraftonk8s.agones.model.Fleet;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerFleet;
import dev.marfien.minecraftonk8s.common.Constant;
import dev.marfien.minecraftonk8s.common.Constant.AppLabel;
import dev.marfien.minecraftonk8s.common.Constant.K8sLabel;
import dev.marfien.minecraftonk8s.operator.dependentresource.minecraftserver.AgonesFleetDependentResource;
import dev.marfien.minecraftonk8s.operator.service.MinecraftServerFleetService;
import io.javaoperatorsdk.operator.api.reconciler.Cleaner;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.DeleteControl;
import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusUpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Workflow;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import jakarta.inject.Inject;

@Workflow(dependents = {
        @Dependent(type = AgonesFleetDependentResource.class)
})
@ControllerConfiguration
public class MinecraftServerFleetReconciler implements Reconciler<MinecraftServerFleet>, Cleaner<MinecraftServerFleet> {

    public static final String LABEL_SELECTOR =
            K8sLabel.MANAGED_BY + "=" + Constant.OPERATOR_NAME + "," +
            AppLabel.RECONCILER + "=" + Constant.MinecraftServerFleet.RECONCILER;

    @Inject
    MinecraftServerFleetService fleetService;

    @Override
    public UpdateControl<MinecraftServerFleet> reconcile(MinecraftServerFleet resource, Context<MinecraftServerFleet> context) {
        Fleet dependent = context.getSecondaryResource(Fleet.class).orElseThrow();

        return UpdateControl.patchStatus(
                this.fleetService.patch(resource, dependent)
        );
    }

    @Override
    public ErrorStatusUpdateControl<MinecraftServerFleet> updateErrorStatus(MinecraftServerFleet resource, Context<MinecraftServerFleet> context, Exception e) {
        return ErrorStatusUpdateControl.patchStatus(
                this.fleetService.patchErrorStatus(resource, e)
        );
    }

    @Override
    public DeleteControl cleanup(MinecraftServerFleet resource, Context<MinecraftServerFleet> context) {
        return DeleteControl.defaultDelete();
    }

}
