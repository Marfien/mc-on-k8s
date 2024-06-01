package dev.marfien.minecraftonk8s.operator.minecraftserver.dependentresource;

import dev.marfien.minecraftonk8s.api.model.GameServer;
import dev.marfien.minecraftonk8s.api.model.GameServerBuilder;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServer;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServerSpec;
import dev.marfien.minecraftonk8s.operator.minecraftserver.MinecraftServerReconciler;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import java.util.List;
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
        PodTemplateSpec template = spec.getTemplate();

        return new GameServerBuilder()
                .withNewMetadata()
                    .withName(primary.getMetadata().getName())
                    .addToLabels(LABELS)
                    .withNamespace(primary.getMetadata().getNamespace())
                    .endMetadata()
                .withNewSpec()
                    .withNewSdkServer()
                        .withLogLevel(spec.getSdkServerLogLevel())
                        .endSdkServer()
                    .withNewTemplateLike(template)
//                        .editSpec()
//                            .withContainers(patchContainers(template.getSpec().getContainers(), spec))
//                            .endSpec()
                        .endTemplate()
                    .endSpec()
                .build();
    }

    private List<Container> patchContainers(List<Container> containers, MinecraftServerSpec spec) {
        return containers.stream()
                .parallel()
                .map(container ->
                        container.edit()
                                .addNewEnv()
                                .withName("MCS_TAGS")
                                .withValue(String.join(";", spec.getTags()))
                                .endEnv()
                                .build()
                )
                .toList();
    }
}
