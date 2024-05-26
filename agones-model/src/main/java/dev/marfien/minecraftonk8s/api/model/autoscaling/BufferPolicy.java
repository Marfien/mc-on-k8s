package dev.marfien.minecraftonk8s.api.model.autoscaling;

import io.fabric8.kubernetes.api.model.IntOrString;
import io.sundr.builder.annotations.Buildable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
@NoArgsConstructor
@AllArgsConstructor
public class BufferPolicy {

    private int maxReplicas;

    private int minReplicas;

    private IntOrString bufferSize;

}
