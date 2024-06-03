package dev.marfien.minecraftonk8s.agones.model.allocation;

import io.sundr.builder.annotations.Buildable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class CounterSelector {

    private long minCount;

    private long maxCount;

    private long minAvailable;

    private long maxAvailable;

}
