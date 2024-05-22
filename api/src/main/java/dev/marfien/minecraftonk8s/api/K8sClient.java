package dev.marfien.minecraftonk8s.api;

import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.dsl.PodResource;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import tr.com.infumia.agones4j.Agones;

public class K8sClient {

    public static Agones createAgonesClientAgones(boolean withGameServerWatcherExecutor, Duration gameServerWatcherExecutorInterval) {
        Agones.Builder builder = Agones.builder();
        if (withGameServerWatcherExecutor) {
            builder.withGameServerWatcherExecutor(Executors.newSingleThreadExecutor());
        }

        if (gameServerWatcherExecutorInterval != null) {
            builder.withHealthCheck(Duration.ZERO, gameServerWatcherExecutorInterval);
            builder.withHealthCheckExecutor(Executors.newSingleThreadScheduledExecutor());
        }

        return builder.withChannel().build();
    }

    public static void use(Consumer<KubernetesClient> consumer) {
        try (KubernetesClient client = new KubernetesClientBuilder().withConfig(new ConfigBuilder().withTrustCerts().build()).build()) {
            consumer.accept(client);
        }
    }

    public static void self(Consumer<PodResource> consumer) {
        try (KubernetesClient client = createKubernetesClient()) {
            consumer.accept(
                    client.pods()
                            .inNamespace(System.getenv("KUBERNETES_NAMESPACE"))
                            .withName(System.getenv("KUBERNETES_POD_NAME"))
            );
        }
    }

    public static KubernetesClient createKubernetesClient() {
        return new KubernetesClientBuilder().withConfig(new ConfigBuilder().withTrustCerts().build()).build();
    }
}
