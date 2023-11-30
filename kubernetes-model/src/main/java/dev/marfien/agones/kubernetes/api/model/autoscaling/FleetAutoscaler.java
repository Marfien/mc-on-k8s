package dev.marfien.agones.kubernetes.api.model.autoscaling;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Kind;
import io.fabric8.kubernetes.model.annotation.Version;

@Version("v1")
@Group("autoscaling.agones.dev")
@Kind("FleetAutoscaler")
public class FleetAutoscaler extends CustomResource<FleetAutoscalerSpec, FleetAutoscalerStatus>
    implements Namespaced {}
