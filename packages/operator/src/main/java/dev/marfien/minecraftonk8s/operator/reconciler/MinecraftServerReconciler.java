package dev.marfien.minecraftonk8s.operator.reconciler;

import dev.marfien.minecraftonk8s.agones.model.GameServer;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServer;
import dev.marfien.minecraftonk8s.common.Constant;
import dev.marfien.minecraftonk8s.common.Constant.AppLabel;
import dev.marfien.minecraftonk8s.common.Constant.K8sLabel;
import dev.marfien.minecraftonk8s.operator.application.BinariesConfigMapEnforcer;
import dev.marfien.minecraftonk8s.operator.dependentresource.minecraftserver.AgonesGameServerDependentResource;
import dev.marfien.minecraftonk8s.operator.service.ConditionService;
import dev.marfien.minecraftonk8s.operator.service.MinecraftServerService;
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
        @Dependent(type = AgonesGameServerDependentResource.class)
})
@ControllerConfiguration
public class MinecraftServerReconciler implements Reconciler<MinecraftServer>, Cleaner<MinecraftServer> {

    public static final String LABEL_SELECTOR =
            K8sLabel.MANAGED_BY + "=" + Constant.OPERATOR_NAME + "," +
            AppLabel.RECONCILER + "=" + Constant.MinecraftServer.RECONCILER;

    @Inject
    MinecraftServerService minecraftServerService;

    @Override
    public UpdateControl<MinecraftServer> reconcile(MinecraftServer minecraftServer, Context<MinecraftServer> context) {
        GameServer backedGameServer = context.getSecondaryResource(GameServer.class).orElseThrow();

        return UpdateControl.patchStatus(
                this.minecraftServerService.patch(minecraftServer, backedGameServer)
        );
    }

    @Override
    public ErrorStatusUpdateControl<MinecraftServer> updateErrorStatus(MinecraftServer resource, Context<MinecraftServer> context, Exception e) {
        return ErrorStatusUpdateControl.patchStatus(
                this.minecraftServerService.patchErrorStatus(resource, e)
        );
    }

    @Override
    public DeleteControl cleanup(MinecraftServer resource, Context<MinecraftServer> context) {
        return DeleteControl.defaultDelete();
    }
}
