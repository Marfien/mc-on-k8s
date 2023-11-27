package dev.marfien.agones.kubernetes.api.model;

import io.fabric8.kubernetes.api.model.PodTemplateSpec;

import java.util.List;
import java.util.Map;

public class GameServerSpec {

    private String container;

    private List<GameServerPort> ports;

    private Health health;

    private String scheduling;

    private SdkServer sdkServer;

    private PodTemplateSpec template;

    private PlayerSpec players;

    private Map<String, CounterStatus> counters;

    private Map<String, ListStatus> lists;

    private Eviction eviction;

    public String getContainer() {
        return this.container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public List<GameServerPort> getPorts() {
        return this.ports;
    }

    public void setPorts(List<GameServerPort> ports) {
        this.ports = ports;
    }

    public Health getHealth() {
        return this.health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public String getScheduling() {
        return this.scheduling;
    }

    public void setScheduling(String scheduling) {
        this.scheduling = scheduling;
    }

    public SdkServer getSdkServer() {
        return this.sdkServer;
    }

    public void setSdkServer(SdkServer sdkServer) {
        this.sdkServer = sdkServer;
    }

    public PodTemplateSpec getTemplate() {
        return this.template;
    }

    public void setTemplate(PodTemplateSpec template) {
        this.template = template;
    }

    public PlayerSpec getPlayers() {
        return this.players;
    }

    public void setPlayers(PlayerSpec players) {
        this.players = players;
    }

    public Map<String, CounterStatus> getCounters() {
        return this.counters;
    }

    public void setCounters(Map<String, CounterStatus> counters) {
        this.counters = counters;
    }

    public Map<String, ListStatus> getLists() {
        return this.lists;
    }

    public void setLists(Map<String, ListStatus> lists) {
        this.lists = lists;
    }

    public Eviction getEviction() {
        return this.eviction;
    }

    public void setEviction(Eviction eviction) {
        this.eviction = eviction;
    }
}
