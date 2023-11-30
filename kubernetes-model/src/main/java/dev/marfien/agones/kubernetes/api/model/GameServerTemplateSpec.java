package dev.marfien.agones.kubernetes.api.model;

import io.fabric8.kubernetes.api.model.ObjectMeta;

public class GameServerTemplateSpec {

  private ObjectMeta metadata;

  private GameServerSpec spec;

  public ObjectMeta getMetadata() {
    return this.metadata;
  }

  public void setMetadata(ObjectMeta metadata) {
    this.metadata = metadata;
  }

  public GameServerSpec getSpec() {
    return this.spec;
  }

  public void setSpec(GameServerSpec spec) {
    this.spec = spec;
  }
}
