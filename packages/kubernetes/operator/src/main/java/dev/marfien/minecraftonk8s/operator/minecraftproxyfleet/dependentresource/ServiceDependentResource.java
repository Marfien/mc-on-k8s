package dev.marfien.minecraftonk8s.operator.minecraftproxyfleet.dependentresource;

import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.MinecraftProxyFleet;
import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.MinecraftProxyFleetSpec;
import dev.marfien.minecraftonk8s.common.Label;
import dev.marfien.minecraftonk8s.operator.minecraftproxyfleet.MinecraftProxyFleetReconciler;
import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.javaoperatorsdk.operator.api.config.informer.Informer;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;

@KubernetesDependent(informer = @Informer(labelSelector = MinecraftProxyFleetReconciler.SELECTOR))
public class ServiceDependentResource extends CRUDKubernetesDependentResource<Service, MinecraftProxyFleet> {

    public ServiceDependentResource() {
        super(Service.class);
    }

    @Override
    protected Service desired(MinecraftProxyFleet primary, Context<MinecraftProxyFleet> context) {
        MinecraftProxyFleetSpec spec = primary.getSpec();
        ObjectMeta meta = primary.getMetadata();
        return new ServiceBuilder()
                .withNewMetadata()
                    .withName(meta.getName())
                    .withNamespace(meta.getNamespace())
                    .addToLabels(MinecraftProxyFleetReconciler.SELECTOR, "true")
                .endMetadata()
                .withNewSpec()
                    .withType(spec.getServiceType().name())
                    .addToSelector(Label.BELONGS_TO.getName(), meta.getUid())
                    .addToSelector(Label.PROXY_STATE.getName(), "running")
                    .addNewPort()
                        .withName("minecraft")
                        .withProtocol("TCP")
                        .withPort(25565)
                        .withTargetPort(new IntOrString(25565))
                    .endPort()
                .endSpec()
                .build();

    }
}
