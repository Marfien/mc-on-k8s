package dev.marfien.minecraftonk8s.api.model;

import dev.marfien.minecraftonk8s.agones.model.autoscaling.FleetAutoscalerPolicy;
import io.fabric8.kubernetes.api.builder.Editable;
import io.sundr.builder.annotations.Buildable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class FleetAutoScalingSpec implements Editable<FleetAutoScalingSpecBuilder> {

    // TODO
    private FleetAutoscalerPolicy policy;

    @Override
    public FleetAutoScalingSpecBuilder edit() {
        return new FleetAutoScalingSpecBuilder(this);
    }
}
