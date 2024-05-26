package dev.marfien.minecraftonk8s.operator.reconciler;

import dev.marfien.minecraftonk8s.api.model.GameServer;
import dev.marfien.minecraftonk8s.api.model.GameServerBuilder;
import dev.marfien.minecraftonk8s.api.model.GameServerStatus;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServer;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServerSpec;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServerStatus;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.OwnerReference;
import io.fabric8.kubernetes.api.model.OwnerReferenceBuilder;
import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.EventSourceContext;
import io.javaoperatorsdk.operator.api.reconciler.EventSourceInitializer;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.processing.event.source.EventSource;
import java.util.List;
import java.util.Map;

public class MinecraftServerReconciler implements Reconciler<MinecraftServer>,
        EventSourceInitializer<MinecraftServer> {

    private final KubernetesClient kubernetesClient;

    public MinecraftServerReconciler(KubernetesClient kubernetesClient) {
        this.kubernetesClient = kubernetesClient;
    }

    private static OwnerReference createOwnerReference(HasMetadata minecraftServer) {
        return new OwnerReferenceBuilder()
                .withApiVersion(minecraftServer.getApiVersion())
                .withKind(minecraftServer.getKind())
                .withName(minecraftServer.getMetadata().getName())
                .withUid(minecraftServer.getMetadata().getUid())
                .withController(true)
                .build();
    }

    @Override
    public UpdateControl<MinecraftServer> reconcile(MinecraftServer minecraftServer,
            Context<MinecraftServer> context) throws Exception {
        ObjectMeta meta = minecraftServer.getMetadata();
        String name = meta.getName();
        String namespace = meta.getNamespace();

        MinecraftServerSpec spec = minecraftServer.getSpec();
        PodTemplateSpec template = spec.getTemplate();

        GameServer gameServer = new GameServerBuilder()
                .withNewMetadata()
                .withName(name)
                .withNamespace(namespace)
                .withOwnerReferences(createOwnerReference(minecraftServer))
                .endMetadata()
                .withNewSpec()
                .withNewSdkServer()
                .withLogLevel(spec.getSdkServerLogLevel())
                .endSdkServer()
                .withNewTemplateLike(template)
                .editSpec()
                .withContainers(patchContainers(template.getSpec().getContainers(), spec))
                .endSpec()
                .endTemplate()
                .endSpec()
                .build();

        GameServer resulting = this.kubernetesClient.resources(GameServer.class)
                .resource(gameServer).serverSideApply();
        GameServerStatus resultingStatus = resulting.getStatus();
        MinecraftServerStatus status = minecraftServer.edit()
                .editStatus()
                .withIp(resultingStatus.getAddress())
                .withPort(25565)
                .withReady(false)
                .endStatus()
                .build();

        return null;
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
                ).toList();
    }

    @Override
    public Map<String, EventSource> prepareEventSources(
            EventSourceContext<MinecraftServer> eventSourceContext) {

        return EventSourceInitializer.nameEventSources();
    }
}
