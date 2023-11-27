package dev.marfien.agones.kubernetes.api.model.allocation;

import dev.marfien.agones.kubernetes.api.model.Priority;

import java.util.List;
import java.util.Map;

public class GameServerAllocationSpec {

    private MultiClusterSetting multiClusterSetting;

    private GameServerSelector required;

    private List<GameServerSelector> preferred;

    private List<Priority> priorities;

    private List<GameServerSelector> selectors;

    private String scheduling;

    private MetaPatch metadata;

    private Map<String, CounterAction> counters;

    private Map<String, ListAction> lists;

    public MultiClusterSetting getMultiClusterSetting() {
        return this.multiClusterSetting;
    }

    public void setMultiClusterSetting(MultiClusterSetting multiClusterSetting) {
        this.multiClusterSetting = multiClusterSetting;
    }

    public GameServerSelector getRequired() {
        return this.required;
    }

    public void setRequired(GameServerSelector required) {
        this.required = required;
    }

    public List<GameServerSelector> getPreferred() {
        return this.preferred;
    }

    public void setPreferred(List<GameServerSelector> preferred) {
        this.preferred = preferred;
    }

    public List<Priority> getPriorities() {
        return this.priorities;
    }

    public void setPriorities(List<Priority> priorities) {
        this.priorities = priorities;
    }

    public List<GameServerSelector> getSelectors() {
        return this.selectors;
    }

    public void setSelectors(List<GameServerSelector> selectors) {
        this.selectors = selectors;
    }

    public String getScheduling() {
        return this.scheduling;
    }

    public void setScheduling(String scheduling) {
        this.scheduling = scheduling;
    }

    public MetaPatch getMetadata() {
        return this.metadata;
    }

    public void setMetadata(MetaPatch metadata) {
        this.metadata = metadata;
    }

    public Map<String, CounterAction> getCounters() {
        return this.counters;
    }

    public void setCounters(Map<String, CounterAction> counters) {
        this.counters = counters;
    }

    public Map<String, ListAction> getLists() {
        return this.lists;
    }

    public void setLists(Map<String, ListAction> lists) {
        this.lists = lists;
    }
}
