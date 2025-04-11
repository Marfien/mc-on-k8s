package dev.marfien.minecraftonk8s.operator.util;

import dev.marfien.minecraftonk8s.agones.model.GameServerSpec;
import dev.marfien.minecraftonk8s.agones.model.GameServerSpecBuilder;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServerSpec;
import dev.marfien.minecraftonk8s.common.Constant;
import dev.marfien.minecraftonk8s.common.Constant.AppLabel;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerPort;
import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.client.CustomResource;
import java.util.List;
import java.util.Objects;

public final class CrdUtils {

    private CrdUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static ObjectMeta clonePrimaryMeta(CustomResource<?, ?> primary, String reconciler) {
        ObjectMeta primaryMetadata = primary.getMetadata();

        return new ObjectMetaBuilder()
                .withName(primaryMetadata.getName())
                .withNamespace(primaryMetadata.getNamespace())
                .withLabels(primaryMetadata.getLabels())
                .addToLabels(Constant.K8sLabel.MANAGED_BY, Constant.OPERATOR_NAME)
                .addToLabels(AppLabel.RECONCILER, reconciler)
                .withAnnotations(primaryMetadata.getAnnotations())
                .build();
    }

    public static GameServerSpec toGameServerSpec(MinecraftServerSpec spec) {
        return new GameServerSpecBuilder()
                .withNewSdkServer()
                    .withLogLevel(spec.getSdkServerLogLevel())
                    .endSdkServer()
                .withNewTemplateLike(spec.getTemplate())
                    .editSpec()
                        .withContainers(patchContainers(spec.getTemplate().getSpec().getContainers(), spec))
                        .endSpec()
                    .endTemplate()
                .build();
    }

    private static List<Container> patchContainers(List<Container> containers, MinecraftServerSpec spec) {
        return containers.stream()
                .parallel()
                .map(container ->
                        container.edit()
                                .addNewEnv()
                                    .withName(Constant.Env.CONFIG_ALLOCATION_STRATEGY)
                                    .withValue(spec.getAllocationStrategy().name())
                                    .endEnv()
                                .build()
                )
                .toList();
    }

    public static boolean containerPortEquals(ContainerPort port, IntOrString other) {
        return Objects.equals(port.getContainerPort(), other.getIntVal())
                || Objects.equals(port.getName(), other.getStrVal());
    }

}
