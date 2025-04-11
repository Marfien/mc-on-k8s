package dev.marfien.minecraftonk8s.operator.dependentresource.minecraftserver;

import dev.marfien.minecraftonk8s.agones.model.Fleet;
import dev.marfien.minecraftonk8s.agones.model.FleetBuilder;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerFleet;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerFleetSpec;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerSpecTemplate;
import dev.marfien.minecraftonk8s.common.Constant;
import dev.marfien.minecraftonk8s.operator.application.BinariesConfigMapEnforcer;
import dev.marfien.minecraftonk8s.operator.util.CrdUtils;
import dev.marfien.minecraftonk8s.operator.reconciler.MinecraftServerFleetReconciler;
import io.javaoperatorsdk.operator.api.config.informer.Informer;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import jakarta.inject.Inject;

@KubernetesDependent(informer = @Informer(labelSelector = MinecraftServerFleetReconciler.LABEL_SELECTOR))
public class AgonesFleetDependentResource extends CRUDKubernetesDependentResource<Fleet, MinecraftServerFleet> {

    @Inject
    BinariesConfigMapEnforcer binariesConfigMapEnforcer;

    public AgonesFleetDependentResource() {
        super(Fleet.class);
    }

    @Override
    protected Fleet desired(MinecraftServerFleet primary, Context<MinecraftServerFleet> context) {
        this.binariesConfigMapEnforcer.ensureAgentBinary();

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
