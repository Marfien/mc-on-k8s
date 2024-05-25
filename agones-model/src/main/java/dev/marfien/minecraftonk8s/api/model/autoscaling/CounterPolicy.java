package dev.marfien.minecraftonk8s.api.model.autoscaling;

import io.fabric8.kubernetes.api.model.IntOrString;
import lombok.AllArgsConstructor;
import io.sundr.builder.annotations.Buildable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
@NoArgsConstructor
@AllArgsConstructor
public class CounterPolicy {

    private String key;

    private long maxCapacity;

    private long minCapacity;

    private IntOrString bufferSize;

}
