package dev.marfien.minecraftonk8s.client.common.adapter;

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
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void self(Consumer<PodResource> action) {
        try (KubernetesClient client = new KubernetesClientBuilder().build()) {
            action.accept(client.pods().inNamespace(this.namespace).withName(this.name));
        }
    }

}
