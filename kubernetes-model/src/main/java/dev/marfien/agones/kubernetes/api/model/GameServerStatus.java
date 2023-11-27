package dev.marfien.agones.kubernetes.api.model;

import io.fabric8.kubernetes.api.model.NodeAddress;

import java.util.List;
import java.util.Map;

public class GameServerStatus {

    private String state;

    private List<GameServerStatusPort> ports;

    private String address;

    private List<NodeAddress> addresses;

    private String nodeName;

    private String reservedUntil;

    private PlayerStatus players;

    private Map<String, CounterStatus> counters;

    private Map<String, ListStatus> lists;

    private Eviction eviction;

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<GameServerStatusPort> getPorts() {
        return this.ports;
    }

    public void setPorts(List<GameServerStatusPort> ports) {
        this.ports = ports;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<NodeAddress> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(List<NodeAddress> addresses) {
        this.addresses = addresses;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getReservedUntil() {
        return this.reservedUntil;
    }

    public void setReservedUntil(String reservedUntil) {
        this.reservedUntil = reservedUntil;
    }

    public PlayerStatus getPlayers() {
        return this.players;
    }

    public void setPlayers(PlayerStatus players) {
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
