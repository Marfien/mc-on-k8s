package dev.marfien.agones.kubernetes.api.model;

public class Health {

  private boolean disabled;

  private int periodSeconds;

  private int failureThreshold;

  private int initialDelaySeconds;

  public boolean isDisabled() {
    return this.disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public int getPeriodSeconds() {
    return this.periodSeconds;
  }

  public void setPeriodSeconds(int periodSeconds) {
    this.periodSeconds = periodSeconds;
  }

  public int getFailureThreshold() {
    return this.failureThreshold;
  }

  public void setFailureThreshold(int failureThreshold) {
    this.failureThreshold = failureThreshold;
  }

  public int getInitialDelaySeconds() {
    return this.initialDelaySeconds;
  }

  public void setInitialDelaySeconds(int initialDelaySeconds) {
    this.initialDelaySeconds = initialDelaySeconds;
  }
}
