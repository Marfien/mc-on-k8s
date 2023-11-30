package dev.marfien.agones.kubernetes.api.model;

import io.fabric8.kubernetes.api.model.apps.DeploymentStrategy;
import java.util.List;

public class FleetSpec {

  private int replicas;

  private AllocationOverflow allocationOverflow;

  private DeploymentStrategy deploymentStrategy;

  private String scheduling;

  private List<Priority> priorities;

  private GameServerTemplateSpec template;

  public int getReplicas() {
    return this.replicas;
  }

  public void setReplicas(int replicas) {
    this.replicas = replicas;
  }

  public AllocationOverflow getAllocationOverflow() {
    return this.allocationOverflow;
  }

  public void setAllocationOverflow(AllocationOverflow allocationOverflow) {
    this.allocationOverflow = allocationOverflow;
  }

  public DeploymentStrategy getDeploymentStrategy() {
    return this.deploymentStrategy;
  }

  public void setDeploymentStrategy(DeploymentStrategy deploymentStrategy) {
    this.deploymentStrategy = deploymentStrategy;
  }

  public String getScheduling() {
    return this.scheduling;
  }

  public void setScheduling(String scheduling) {
    this.scheduling = scheduling;
  }

  public List<Priority> getPriorities() {
    return this.priorities;
  }

  public void setPriorities(List<Priority> priorities) {
    this.priorities = priorities;
  }

  public GameServerTemplateSpec getTemplate() {
    return this.template;
  }

  public void setTemplate(GameServerTemplateSpec template) {
    this.template = template;
  }
}
