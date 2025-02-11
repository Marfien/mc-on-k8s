package dev.marfien.minecraftonk8s.operator.minecraftproxyfleet;

import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.MinecraftProxyFleet;
import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.MinecraftProxyFleetSpec;
import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.ServiceSpec;
import dev.marfien.minecraftonk8s.common.Constant;
import dev.marfien.minecraftonk8s.common.Constant.AppLabel;
import dev.marfien.minecraftonk8s.common.Constant.ProxyState;
import dev.marfien.minecraftonk8s.operator.CrdUtils;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.javaoperatorsdk.operator.api.config.informer.Informer;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;

@KubernetesDependent(informer = @Informer(labelSelector = MinecraftProxyFleetReconciler.LABEL_SELECTOR))
public class ServiceDependentResource extends CRUDKubernetesDependentResource<Service, MinecraftProxyFleet> {

    public ServiceDependentResource() {
        super(Service.class);
    }

    @Override
    protected Service desired(MinecraftProxyFleet primary, Context<MinecraftProxyFleet> context) {
        MinecraftProxyFleetSpec spec = primary.getSpec();
        ObjectMeta meta = primary.getMetadata();

        ServiceSpec serviceSpec = spec.getService();

        return new ServiceBuilder()
                .withMetadata(CrdUtils.clonePrimaryMeta(primary, Constant.MinecraftProxyFleet.RECONCILER))
                .withNewSpec()
                    .withType(serviceSpec.getType().getKubeType())
                    .addToSelector(AppLabel.BELONGS_TO, meta.getUid())
                    .addToSelector(AppLabel.PROXY_STATE, ProxyState.RUNNING)
                    .addNewPort()
                        .withProtocol(serviceSpec.getProtocol())
                        .withPort(serviceSpec.getPort())
                        .withTargetPort(serviceSpec.getTargetPort())
                        .endPort()
                    .endSpec()
                .build();

    }
}
