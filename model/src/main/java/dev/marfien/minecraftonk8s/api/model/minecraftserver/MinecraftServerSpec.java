package dev.marfien.minecraftonk8s.api.model.minecraftserver;

import io.fabric8.kubernetes.api.builder.Editable;
import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder", refs = {
        @BuildableReference(PodTemplateSpec.class)
})
public class MinecraftServerSpec implements Editable<MinecraftServerSpecBuilder> {

    private Set<String> clusterRef;
    private Set<String> tags;

    private PodTemplateSpec template;

    @Override
    public MinecraftServerSpecBuilder edit() {
        return new MinecraftServerSpecBuilder(this);
    }
}
