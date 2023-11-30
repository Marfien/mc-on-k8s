package dev.marfien.agones.kubernetes.api.model;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Kind;
import io.fabric8.kubernetes.model.annotation.Version;

@Version("v1")
@Group("agones.dev")
@Kind("Fleet")
public class Fleet extends CustomResource<FleetSpec, FleetStatus> implements Namespaced {}
