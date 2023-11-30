package dev.marfien.agones.kubernetes.api.model;

import java.util.Map;

public class GameServerSetStatus {

  private int replicas;

  private int readyReplicas;

  private int reservedReplicas;

  private int allocatedReplicas;

  private int shutdownReplicas;

  private AggregatedPlayerStatus players;

  private Map<String, AggregatedCounterStatus> counters;

  private Map<String, AggregatedListStatus> lists;

  public int getReplicas() {
    return this.replicas;
  }

  public void setReplicas(int replicas) {
    this.replicas = replicas;
  }

  public int getReadyReplicas() {
    return this.readyReplicas;
  }

  public void setReadyReplicas(int readyReplicas) {
    this.readyReplicas = readyReplicas;
  }

  public int getReservedReplicas() {
    return this.reservedReplicas;
  }

  public void setReservedReplicas(int reservedReplicas) {
    this.reservedReplicas = reservedReplicas;
  }

  public int getAllocatedReplicas() {
    return this.allocatedReplicas;
  }

  public void setAllocatedReplicas(int allocatedReplicas) {
    this.allocatedReplicas = allocatedReplicas;
  }

  public int getShutdownReplicas() {
    return this.shutdownReplicas;
  }

  public void setShutdownReplicas(int shutdownReplicas) {
    this.shutdownReplicas = shutdownReplicas;
  }

  public AggregatedPlayerStatus getPlayers() {
    return this.players;
  }

  public void setPlayers(AggregatedPlayerStatus players) {
    this.players = players;
  }

  public Map<String, AggregatedCounterStatus> getCounters() {
    return this.counters;
  }

  public void setCounters(Map<String, AggregatedCounterStatus> counters) {
    this.counters = counters;
  }

  public Map<String, AggregatedListStatus> getLists() {
    return this.lists;
  }

  public void setLists(Map<String, AggregatedListStatus> lists) {
    this.lists = lists;
  }
}
