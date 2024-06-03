package dev.marfien.minecraftonk8s.operator.minecraftserverfleet.dependentresource;

import dev.marfien.minecraftonk8s.agones.model.Fleet;
import dev.marfien.minecraftonk8s.agones.model.FleetBuilder;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServerSpec;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerFleet;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerFleetSpec;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerTemplateSpec;
import dev.marfien.minecraftonk8s.operator.minecraftserver.MinecraftServerReconciler;
import dev.marfien.minecraftonk8s.operator.minecraftserverfleet.MinecraftServerFleetReconciler;
import io.fabric8.kubernetes.api.model.Container;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import java.util.List;
import java.util.Map;

@KubernetesDependent(labelSelector = MinecraftServerReconciler.SELECTOR)
public class FleetDependentResource extends
        CRUDKubernetesDependentResource<Fleet, MinecraftServerFleet> {

    private static final Map<String, String> LABELS = Map.of(MinecraftServerFleetReconciler.SELECTOR, "true");

    public FleetDependentResource() {
        super(Fleet.class);
    }

    @Override
    protected Fleet desired(MinecraftServerFleet primary, Context<MinecraftServerFleet> context) {

        MinecraftServerFleetSpec spec = primary.getSpec();
        MinecraftServerTemplateSpec template = spec.getTemplate();

        return new FleetBuilder()
                .withNewMetadata()
                    .withName(primary.getMetadata().getName())
                    .addToLabels(LABELS)
                    .withNamespace(primary.getMetadata().getNamespace())
                    .endMetadata()
                .withNewSpec()
                    .withNewTemplate()
                        .withMetadata(template.getMetadata())
                        .withNewSpec()
                            .withNewSdkServer()
                                .withLogLevel(template.getSpec().getSdkServerLogLevel())
                                .endSdkServer()
                            .withNewTemplateLike(template.getSpec().getTemplate())
                                .editSpec()
                                    .withContainers(patchContainers(template.getSpec().getTemplate().getSpec().getContainers(), template.getSpec()))
                                    .endSpec()
                                .endTemplate()
                            .endSpec()
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
