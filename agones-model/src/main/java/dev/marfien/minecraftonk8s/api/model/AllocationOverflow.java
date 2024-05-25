package dev.marfien.minecraftonk8s.api.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import io.sundr.builder.annotations.Buildable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
@NoArgsConstructor
@AllArgsConstructor
public class AllocationOverflow {

    private Map<String, String> labels;

    private Map<String, String> annotations;

}
