package dev.marfien.minecraftonk8s.operator.reconciler;

import dev.marfien.minecraftonk8s.api.model.GameServer;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServer;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;

public class MinecraftServerReconciler implements Reconciler<MinecraftServer> {

    private final KubernetesClient kubernetesClient;

    public MinecraftServerReconciler(KubernetesClient kubernetesClient) {
        this.kubernetesClient = kubernetesClient;
    }

    @Override
    public UpdateControl<MinecraftServer> reconcile(MinecraftServer minecraftServer, Context<MinecraftServer> context) throws Exception {
        ObjectMeta meta = minecraftServer.getMetadata();
        String name = meta.getName();
        String namespace = meta.getNamespace();

        this.kubernetesClient.resources(GameServer.class);

        return null;
    }

}
