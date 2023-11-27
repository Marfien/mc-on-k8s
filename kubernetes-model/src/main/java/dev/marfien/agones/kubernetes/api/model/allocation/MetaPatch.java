package dev.marfien.agones.kubernetes.api.model.allocation;

import java.util.Map;

public class MetaPatch {

    private Map<String, String> labels;

    private Map<String, String> annotations;

    public Map<String, String> getLabels() {
        return this.labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public Map<String, String> getAnnotations() {
        return this.annotations;
    }

    public void setAnnotations(Map<String, String> annotations) {
        this.annotations = annotations;
    }
}
