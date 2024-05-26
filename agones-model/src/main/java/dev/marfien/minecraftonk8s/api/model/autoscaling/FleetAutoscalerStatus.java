package dev.marfien.minecraftonk8s.api.model.autoscaling;

import io.sundr.builder.annotations.Buildable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
@NoArgsConstructor
@AllArgsConstructor
public class FleetAutoscalerStatus {

    private int currentReplicas;

    private int desiredReplicas;

    private String lastScaleTime;

    private boolean ableToScale;

    private boolean scalingLimited;

}
