package dev.marfien.minecraftonk8s.operator.minecraftserver.dependentresource;

import dev.marfien.minecraftonk8s.agones.model.GameServer;
import dev.marfien.minecraftonk8s.agones.model.GameServerBuilder;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServer;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServerSpec;
import dev.marfien.minecraftonk8s.common.Label;
import dev.marfien.minecraftonk8s.operator.minecraftserver.MinecraftServerReconciler;
import dev.marfien.minecraftonk8s.operator.util.GameServerUtil;
import io.javaoperatorsdk.operator.api.config.informer.Informer;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.util.Map;

@KubernetesDependent(informer = @Informer(labelSelector = MinecraftServerReconciler.SELECTOR))
public class GameServerDependentResource extends
        CRUDKubernetesDependentResource<GameServer, MinecraftServer> {

    private static final Map<String, String> LABELS = Map.of(Label.CONTROLLED_BY.getName(), "mc-server-reconciler");

    @ConfigProperty(name = "minecraftonk8s.gameserver.allocation-strategy.default")
    String defaultAllocationStrategy;

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
