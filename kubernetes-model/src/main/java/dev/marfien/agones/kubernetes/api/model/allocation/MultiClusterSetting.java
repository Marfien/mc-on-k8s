package dev.marfien.agones.kubernetes.api.model.allocation;

import io.fabric8.kubernetes.api.model.LabelSelector;

public class MultiClusterSetting {

  private boolean enabled;

  private LabelSelector policySelector;

  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public LabelSelector getPolicySelector() {
    return this.policySelector;
  }

  public void setPolicySelector(LabelSelector policySelector) {
    this.policySelector = policySelector;
  }
}
