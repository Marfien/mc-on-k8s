package dev.marfien.agones.kubernetes.api.model.allocation;

import io.fabric8.kubernetes.api.model.LabelSelector;

import java.util.Map;

public class GameServerSelector {

    private LabelSelector labelSelector;

    private String gameServerState;

    private PlayerSelector players;

    private Map<String, CounterSelector> counters;

    private Map<String, ListSelector> lists;

    public LabelSelector getLabelSelector() {
        return this.labelSelector;
    }

    public void setLabelSelector(LabelSelector labelSelector) {
        this.labelSelector = labelSelector;
    }

    public String getGameServerState() {
        return this.gameServerState;
    }

    public void setGameServerState(String gameServerState) {
        this.gameServerState = gameServerState;
    }

    public PlayerSelector getPlayers() {
        return this.players;
    }

    public void setPlayers(PlayerSelector players) {
        this.players = players;
    }

    public Map<String, CounterSelector> getCounters() {
        return this.counters;
    }

    public void setCounters(Map<String, CounterSelector> counters) {
        this.counters = counters;
    }

    public Map<String, ListSelector> getLists() {
        return this.lists;
    }

    public void setLists(Map<String, ListSelector> lists) {
        this.lists = lists;
    }
}
