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

    public static Agones createAgonesClientAgones() {
        return Agones.builder().withChannel().build();
    }

    public static void useClient(Consumer<KubernetesClient> consumer) {
        try (KubernetesClient client = createKubernetesClient()) {
            consumer.accept(client);
        }
    }

    public static void self(Consumer<PodResource> consumer) {
        useClient(client -> {
            PodResource pod = client.pods()
                .inNamespace(System.getenv("POD_NAMESPACE"))
                .withName(System.getenv("GAMESERVER_NAME"));
            consumer.accept(pod);
        });
    }

    public static KubernetesClient createKubernetesClient() {
        return new KubernetesClientBuilder()
            .withConfig(new ConfigBuilder().withTrustCerts().build())
            .build();
    }
}
