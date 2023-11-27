package dev.marfien.agones.kubernetes.api.model;

import java.util.List;

public class GameServerSetSpec {

    private int replicas;

    private AllocationOverflow allocationOverflow;

    private String scheduling;

    private List<Priority> priorities;

    private GameServerTemplateSpec template;

    public int getReplicas() {
        return this.replicas;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }

    public AllocationOverflow getAllocationOverflow() {
        return this.allocationOverflow;
    }

    public void setAllocationOverflow(AllocationOverflow allocationOverflow) {
        this.allocationOverflow = allocationOverflow;
    }

    public String getScheduling() {
        return this.scheduling;
    }

    public void setScheduling(String scheduling) {
        this.scheduling = scheduling;
    }

    public List<Priority> getPriorities() {
        return this.priorities;
    }

    public void setPriorities(List<Priority> priorities) {
        this.priorities = priorities;
    }

    public GameServerTemplateSpec getTemplate() {
        return this.template;
    }

    public void setTemplate(GameServerTemplateSpec template) {
        this.template = template;
    }
}
