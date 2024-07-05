package dev.marfien.minecraftonk8s.operator.minecraftserver.dependentresource;

import dev.marfien.minecraftonk8s.agones.model.GameServer;
import dev.marfien.minecraftonk8s.agones.model.GameServerBuilder;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServer;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServerSpec;
import dev.marfien.minecraftonk8s.operator.minecraftserver.MinecraftServerReconciler;
import dev.marfien.minecraftonk8s.operator.util.GameServerUtil;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import java.util.Map;

@KubernetesDependent(labelSelector = MinecraftServerReconciler.SELECTOR)
public class GameServerDependentResource extends
        CRUDKubernetesDependentResource<GameServer, MinecraftServer> {

    private static final Map<String, String> LABELS = Map.of(MinecraftServerReconciler.SELECTOR, "true");

    public GameServerDependentResource() {
        super(GameServer.class);
    }

    @Override
    protected GameServer desired(MinecraftServer primary, Context<MinecraftServer> context) {
        MinecraftServerSpec spec = primary.getSpec();
        return new GameServerBuilder()
                .withNewMetadata()
                    .withName(primary.getMetadata().getName())
                    .addToLabels(LABELS)
                    .withNamespace(primary.getMetadata().getNamespace())
                .endMetadata()
                .withSpec(GameServerUtil.toGameServerSpec(spec))
                .build();
    }
}
