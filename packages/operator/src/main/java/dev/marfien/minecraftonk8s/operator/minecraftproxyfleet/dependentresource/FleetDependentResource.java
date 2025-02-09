package dev.marfien.minecraftonk8s.operator.minecraftproxyfleet.dependentresource;

import dev.marfien.minecraftonk8s.agones.model.Fleet;
import dev.marfien.minecraftonk8s.agones.model.FleetBuilder;
import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.MinecraftProxyFleet;
import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.MinecraftProxyFleetSpec;
import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.MinecraftProxySpec;
import dev.marfien.minecraftonk8s.common.Label;
import dev.marfien.minecraftonk8s.operator.minecraftproxyfleet.MinecraftProxyFleetReconciler;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.javaoperatorsdk.operator.api.config.informer.Informer;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import java.util.List;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@KubernetesDependent(informer = @Informer(labelSelector = MinecraftProxyFleetReconciler.SELECTOR))
public class FleetDependentResource extends CRUDKubernetesDependentResource<Fleet, MinecraftProxyFleet> {

    @ConfigProperty(name = "minecraftonk8s.proxy.allocation-strategy.default")
    String proxyDefaultAllocationStrategy;

    @ConfigProperty(name = "minecraftonk8s.proxy.serviceaccount.name")
    String proxyServiceAccount;

    public FleetDependentResource() {
        super(Fleet.class);
    }

    @Override
    protected Fleet desired(MinecraftProxyFleet primary, Context<MinecraftProxyFleet> context) {
        MinecraftProxyFleetSpec spec = primary.getSpec();
        MinecraftProxySpec template = spec.getTemplate();
        ObjectMeta metadata = primary.getMetadata();

        return new FleetBuilder()
                .withNewMetadata()
                    .withName(metadata.getName())
                    .withNamespace(metadata.getNamespace())
                .endMetadata()
                .withNewSpec()
                    .withDeploymentStrategy(spec.getDeploymentStrategy())
                    .withReplicas(spec.getReplicas())
                    .withNewTemplate()
                        .withNewSpec()
                            .withNewSdkServer()
                                .withLogLevel(template.getSdkServerLogLevel())
                            .endSdkServer()
                            .withNewTemplateLike(template.getTemplate())
                                .editMetadata()
                                    .addToLabels(Label.BELONGS_TO.getName(), metadata.getUid())
                                .endMetadata()
                                .editSpec()
                                    .withServiceAccountName(proxyServiceAccount)
                                    .withContainers(patchContainers(template.getTemplate().getSpec().getContainers(), template, spec))
                                .endSpec()
                            .endTemplate()
                        .endSpec()
                    .endTemplate()
                .endSpec()
                .build();
    }

    private List<Container> patchContainers(List<Container> containers, MinecraftProxySpec template, MinecraftProxyFleetSpec spec) {
        return containers.stream()
                .parallel()
                .map(container ->
                        container.edit()
                                .addNewEnv()
                                    .withName("LABEL_SELECTOR")
                                    .withValue(template.getLabelSelectorString())
                                .endEnv()
                                .addNewEnv()
                                    .withName("PROXY_FLEET_ALLOCATION_DEFAULTSTRATEGY")
                                    .withValue(proxyDefaultAllocationStrategy)
                                .endEnv()
                                .build()
                )
                .toList();
    }
}
