package dev.marfien.minecraftonk8s.api.model.allocation;

import io.sundr.builder.annotations.Buildable;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class MetaPatch {

    private Map<String, String> labels;

    private Map<String, String> annotations;

}
