package dev.marfien.minecraftonk8s.api.model.minecraftserver;

import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import java.util.Set;

public class MinecraftServerSpec {

    private Set<String> clusterRef;
    private Set<String> tags;

    private PodTemplateSpec template;

    public Set<String> getClusterRef() {
        return clusterRef;
    }

    public void setClusterRef(Set<String> clusterRef) {
        this.clusterRef = clusterRef;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public PodTemplateSpec getTemplate() {
        return template;
    }

    public void setTemplate(PodTemplateSpec template) {
        this.template = template;
    }
}
