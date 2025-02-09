package dev.marfien.minecraftonk8s.client.api.adapter;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.dsl.PodResource;
import java.util.function.Consumer;

public class KubernetesAdapter {

    private final String name;
    private final String namespace;

    public KubernetesAdapter(String name, String namespace) {
        this.name = name;
        this.namespace = namespace;
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public void self(Consumer<PodResource> action) {
        try (KubernetesClient client = new KubernetesClientBuilder().build()) {
            action.accept(client.pods().inNamespace(this.namespace).withName(this.name));
        }
    }

}
