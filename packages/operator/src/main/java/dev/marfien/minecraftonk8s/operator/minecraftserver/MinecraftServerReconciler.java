package dev.marfien.minecraftonk8s.operator.minecraftserver;

import dev.marfien.minecraftonk8s.agones.model.GameServer;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServer;
import dev.marfien.minecraftonk8s.operator.minecraftserver.dependentresource.GameServerDependentResource;
import io.javaoperatorsdk.operator.api.reconciler.Cleaner;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.DeleteControl;
import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusUpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Workflow;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;

@Workflow(dependents = {
        @Dependent(type = GameServerDependentResource.class)
})
@ControllerConfiguration
public class MinecraftServerReconciler implements Reconciler<MinecraftServer>, Cleaner<MinecraftServer> {

    public static final String SELECTOR = "mconk8s.marfien.dev/controlled-by=mc-server-reconciler";

    @Override
    public UpdateControl<MinecraftServer> reconcile(
            MinecraftServer minecraftServer, Context<MinecraftServer> context) throws Exception {
        GameServer backedGameServer = context.getSecondaryResource(GameServer.class).orElseThrow();
        String backedGameServerStatus = backedGameServer.getStatus().getState();

        boolean ready = minecraftServer.getStatus().isReady()
                || backedGameServerStatus.equals("Ready")
                || backedGameServerStatus.equals("Reserved")
                || backedGameServerStatus.equals("Allocated");

        return UpdateControl.patchStatus(
                minecraftServer.edit()
                        .editStatus()
                            .withIp(backedGameServer.getStatus().getAddress())
                            // TODO: This should be the actual port of the game server
                            .withPort(25565)
                            .withReady(ready)
                            .addNewCondition()
                                .withStatus(ready ? "True" : "False")
                                .withType("Ready")
                                .withReason("GameServerReady")
                                .withMessage("The game server is ready")
                            .endCondition()
                        .endStatus()
                        .build()
        );
    }


    @Override
    public ErrorStatusUpdateControl<MinecraftServer> updateErrorStatus(MinecraftServer resource,
            Context<MinecraftServer> context, Exception e) {
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
    public DeleteControl cleanup(MinecraftServer resource, Context<MinecraftServer> context) {
        return DeleteControl.defaultDelete();
    }
}
