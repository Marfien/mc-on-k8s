package dev.marfien.minecraftonk8s.api.model.allocation;

import io.fabric8.kubernetes.api.model.LabelSelector;
import io.sundr.builder.annotations.BuildableReference;
import lombok.AllArgsConstructor;
import io.sundr.builder.annotations.Buildable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder", refs = {
        @BuildableReference(LabelSelector.class)
})
public class MultiClusterSetting {

    private boolean enabled;

    private LabelSelector policySelector;

}
