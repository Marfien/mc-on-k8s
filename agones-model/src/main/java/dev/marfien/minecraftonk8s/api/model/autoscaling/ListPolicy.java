package dev.marfien.minecraftonk8s.api.model.autoscaling;

import io.fabric8.kubernetes.api.model.IntOrString;
import io.sundr.builder.annotations.Buildable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class ListPolicy {

    private String key;

    private long maxCapacity;

    private long minCapacity;

    private IntOrString bufferSize;

}
