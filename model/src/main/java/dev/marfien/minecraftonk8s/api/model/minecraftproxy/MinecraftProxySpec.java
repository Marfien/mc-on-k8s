package dev.marfien.minecraftonk8s.api.model.minecraftproxy;

import io.fabric8.kubernetes.api.model.PodTemplateSpec;

public class MinecraftProxySpec {

    private String clusterRef;

    private PodTemplateSpec template;

    public String getClusterRef() {
        return clusterRef;
    }

    public void setClusterRef(String clusterRef) {
        this.clusterRef = clusterRef;
    }

    public PodTemplateSpec getTemplate() {
        return template;
    }

    public void setTemplate(PodTemplateSpec template) {
        this.template = template;
    }
}
