package dev.marfien.minecraftonk8s.operator.minecraftproxyfleet;

import dev.marfien.minecraftonk8s.agones.model.Fleet;
import dev.marfien.minecraftonk8s.agones.model.FleetBuilder;
import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.MinecraftProxyFleet;
import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.MinecraftProxyFleetSpec;
import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.MinecraftProxySpec;
import dev.marfien.minecraftonk8s.common.Constant;
import dev.marfien.minecraftonk8s.common.Constant.AppLabel;
import dev.marfien.minecraftonk8s.operator.Config;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.javaoperatorsdk.operator.api.config.informer.Informer;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import jakarta.inject.Inject;
import java.util.List;

@KubernetesDependent(informer = @Informer(labelSelector = MinecraftProxyFleetReconciler.LABEL_SELECTOR))
public class FleetDependentResource extends CRUDKubernetesDependentResource<Fleet, MinecraftProxyFleet> {

    @Inject
    Config config;

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
                                    .addToLabels(AppLabel.BELONGS_TO, metadata.getUid())
                                    .endMetadata()
                                .editSpec()
                                    .withServiceAccountName(this.config.proxy().serviceaccountName())
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
                                    .withName(Constant.Env.CONFIG_ALLOCATION_STRATEGY)
                                    .withValue(template.getAllocationStrategy().name())
                                    .endEnv()
                                .addNewEnv()
                                    .withName(Constant.Env.PROXY_CONFIG_LABEL_SELECTOR)
                                    .withValue(template.getLabelSelectorString())
                                    .endEnv()
                                .addNewEnv()
                                    .withName(Constant.Env.PROXY_CONFIG_DRAINAGE_DELAY)
                                    .withValue(String.valueOf(template.getDrainage().getDelayHours()))
                                    .endEnv()
                                .addNewEnv()
                                    .withName(Constant.Env.PROXY_CONFIG_DRAINAGE_TIMEOUT)
                                    .withValue(String.valueOf(template.getDrainage().getTimeoutHours()))
                                    .endEnv()
                                .addNewEnv()
                                    .withName(Constant.Env.PROXY_CONFIG_REBUILD_CACHE_INTERVALL)
                                    .withValue(String.valueOf(template.getCacheRebuildIntervalMinutes()))
                                    .endEnv()
                                .build()
                )
                .toList();
    }
}
