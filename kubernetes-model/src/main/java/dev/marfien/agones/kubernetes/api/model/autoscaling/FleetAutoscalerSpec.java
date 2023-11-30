package dev.marfien.agones.kubernetes.api.model.autoscaling;

public class FleetAutoscalerSpec {

  private String fleetName;

  private FleetAutoscalerPolicy policy;

  private FleetAutoscalerSync sync;

  public String getFleetName() {
    return this.fleetName;
  }

  public void setFleetName(String fleetName) {
    this.fleetName = fleetName;
  }

  public FleetAutoscalerPolicy getPolicy() {
    return this.policy;
  }

  public void setPolicy(FleetAutoscalerPolicy policy) {
    this.policy = policy;
  }

  public FleetAutoscalerSync getSync() {
    return this.sync;
  }

  public void setSync(FleetAutoscalerSync sync) {
    this.sync = sync;
  }
}
