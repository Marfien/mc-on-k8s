package dev.marfien.agones.kubernetes.api.model.autoscaling;

public class FleetAutoscalerStatus {

  private int currentReplicas;

  private int desiredReplicas;

  private String lastScaleTime;

  private boolean ableToScale;

  private boolean scalingLimited;

  public int getCurrentReplicas() {
    return this.currentReplicas;
  }

  public void setCurrentReplicas(int currentReplicas) {
    this.currentReplicas = currentReplicas;
  }

  public int getDesiredReplicas() {
    return this.desiredReplicas;
  }

  public void setDesiredReplicas(int desiredReplicas) {
    this.desiredReplicas = desiredReplicas;
  }

  public String getLastScaleTime() {
    return this.lastScaleTime;
  }

  public void setLastScaleTime(String lastScaleTime) {
    this.lastScaleTime = lastScaleTime;
  }

  public boolean isAbleToScale() {
    return this.ableToScale;
  }

  public void setAbleToScale(boolean ableToScale) {
    this.ableToScale = ableToScale;
  }

  public boolean isScalingLimited() {
    return this.scalingLimited;
  }

  public void setScalingLimited(boolean scalingLimited) {
    this.scalingLimited = scalingLimited;
  }
}
