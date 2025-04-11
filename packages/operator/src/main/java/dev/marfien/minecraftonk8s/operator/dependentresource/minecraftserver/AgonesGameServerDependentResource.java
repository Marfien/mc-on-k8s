package dev.marfien.minecraftonk8s.operator.dependentresource.minecraftserver;

import dev.marfien.minecraftonk8s.agones.model.GameServer;
import dev.marfien.minecraftonk8s.agones.model.GameServerBuilder;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServer;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServerSpec;
import dev.marfien.minecraftonk8s.common.Constant;
import dev.marfien.minecraftonk8s.operator.application.BinariesConfigMapEnforcer;
import dev.marfien.minecraftonk8s.operator.util.CrdUtils;
import dev.marfien.minecraftonk8s.operator.reconciler.MinecraftServerReconciler;
import io.javaoperatorsdk.operator.api.config.informer.Informer;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import jakarta.inject.Inject;

@KubernetesDependent(informer = @Informer(labelSelector = MinecraftServerReconciler.LABEL_SELECTOR))
public class AgonesGameServerDependentResource extends CRUDKubernetesDependentResource<GameServer, MinecraftServer> {

    @Inject
    BinariesConfigMapEnforcer binariesConfigMapEnforcer;

    public AgonesGameServerDependentResource() {
        super(GameServer.class);
    }

    @Override
    protected GameServer desired(MinecraftServer primary, Context<MinecraftServer> context) {
        this.binariesConfigMapEnforcer.ensureAgentBinary();

        MinecraftServerSpec spec = primary.getSpec();
        return new GameServerBuilder()
                .withMetadata(CrdUtils.clonePrimaryMeta(primary, Constant.MinecraftServer.RECONCILER))
                .withSpec(CrdUtils.toGameServerSpec(spec))
                .build();
    }
}
