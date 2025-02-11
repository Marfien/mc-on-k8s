package dev.marfien.minecraftonk8s.operator.minecraftserverfleet;

import dev.marfien.minecraftonk8s.agones.model.Fleet;
import dev.marfien.minecraftonk8s.agones.model.FleetBuilder;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerFleet;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerFleetSpec;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerSpecTemplate;
import dev.marfien.minecraftonk8s.common.Constant;
import dev.marfien.minecraftonk8s.operator.CrdUtils;
import io.javaoperatorsdk.operator.api.config.informer.Informer;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;

@KubernetesDependent(informer = @Informer(labelSelector = MinecraftServerFleetReconciler.LABEL_SELECTOR))
public class FleetDependentResource extends
        CRUDKubernetesDependentResource<Fleet, MinecraftServerFleet> {

    public FleetDependentResource() {
        super(Fleet.class);
    }

    @Override
    protected Fleet desired(MinecraftServerFleet primary, Context<MinecraftServerFleet> context) {

        MinecraftServerFleetSpec spec = primary.getSpec();
        MinecraftServerSpecTemplate template = spec.getTemplate();

        return new FleetBuilder()
                .withMetadata(CrdUtils.clonePrimaryMeta(primary, Constant.MinecraftServerFleet.RECONCILER))
                .withNewSpec()
                    .withNewTemplate()
                        .withMetadata(template.getMetadata())
                        .withSpec(CrdUtils.toGameServerSpec(template.getSpec()))
                    .endTemplate()
                .endSpec()
                .build();
    }
}
