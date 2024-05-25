package dev.marfien.minecraftonk8s.operator.reconciler;

import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServer;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;

public class MinecraftServerReconciler implements Reconciler<MinecraftServer> {

    @Override
    public UpdateControl<MinecraftServer> reconcile(MinecraftServer minecraftServer, Context<MinecraftServer> context) throws Exception {
        return null;
    }

}
