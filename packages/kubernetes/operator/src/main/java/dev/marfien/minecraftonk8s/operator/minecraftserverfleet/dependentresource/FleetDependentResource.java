package dev.marfien.minecraftonk8s.operator.minecraftserverfleet.dependentresource;

import dev.marfien.minecraftonk8s.agones.model.Fleet;
import dev.marfien.minecraftonk8s.agones.model.FleetBuilder;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerFleet;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerFleetSpec;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerSpecTemplate;
import dev.marfien.minecraftonk8s.common.Label;
import dev.marfien.minecraftonk8s.operator.minecraftserver.MinecraftServerReconciler;
import dev.marfien.minecraftonk8s.operator.util.GameServerUtil;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import java.util.Map;

@KubernetesDependent(labelSelector = MinecraftServerReconciler.SELECTOR)
public class FleetDependentResource extends
        CRUDKubernetesDependentResource<Fleet, MinecraftServerFleet> {

    private static final Map<String, String> LABELS = Map.of(Label.CONTROLLED_BY.getName(), "fleet-reconciler");

    public FleetDependentResource() {
        super(Fleet.class);
    }

    @Override
    protected Fleet desired(MinecraftServerFleet primary, Context<MinecraftServerFleet> context) {

        MinecraftServerFleetSpec spec = primary.getSpec();
        MinecraftServerSpecTemplate template = spec.getTemplate();

        return new FleetBuilder()
                .withNewMetadata()
                    .withName(primary.getMetadata().getName())
                    .addToLabels(LABELS)
                    .withNamespace(primary.getMetadata().getNamespace())
                .endMetadata()
                .withNewSpec()
                    .withNewTemplate()
                        .withMetadata(template.getMetadata())
                        .withSpec(GameServerUtil.toGameServerSpec(template.getSpec()))
                    .endTemplate()
                .endSpec()
                .build();
    }
}
