package dev.marfien.minecraftonk8s.operator.minecraftserver;

import dev.marfien.minecraftonk8s.agones.model.GameServer;
import dev.marfien.minecraftonk8s.agones.model.GameServerBuilder;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServer;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServerSpec;
import dev.marfien.minecraftonk8s.common.Constant;
import dev.marfien.minecraftonk8s.operator.CrdUtils;
import io.javaoperatorsdk.operator.api.config.informer.Informer;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;

@KubernetesDependent(informer = @Informer(labelSelector = MinecraftServerReconciler.LABEL_SELECTOR))
public class GameServerDependentResource extends
        CRUDKubernetesDependentResource<GameServer, MinecraftServer> {

    public GameServerDependentResource() {
        super(GameServer.class);
    }

    @Override
    protected GameServer desired(MinecraftServer primary, Context<MinecraftServer> context) {
        MinecraftServerSpec spec = primary.getSpec();
        return new GameServerBuilder()
                .withMetadata(CrdUtils.clonePrimaryMeta(primary, Constant.MinecraftServer.RECONCILER))
                .withSpec(CrdUtils.toGameServerSpec(spec))
                .build();
    }
}
