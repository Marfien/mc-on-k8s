package dev.marfien.agones.kubernetes.api.model.autoscaling;

public class FleetAutoscalerSync {

  private String type;

  private FixedIntervalSync fixedInterval;

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public FixedIntervalSync getFixedInterval() {
    return this.fixedInterval;
  }

  public void setFixedInterval(FixedIntervalSync fixedInterval) {
    this.fixedInterval = fixedInterval;
  }
}
