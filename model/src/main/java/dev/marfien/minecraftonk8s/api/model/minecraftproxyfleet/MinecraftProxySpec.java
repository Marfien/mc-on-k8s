package dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet;

import io.fabric8.kubernetes.api.builder.Editable;
import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder", refs = {
        @BuildableReference(PodTemplateSpec.class)
})
public class MinecraftProxySpec implements Editable<MinecraftProxySpecBuilder> {

    private String clusterRef;

    private PodTemplateSpec template;

    private String sdkServerLogLevel = "info";

    @Override
    public MinecraftProxySpecBuilder edit() {
        return new MinecraftProxySpecBuilder(this);
    }
}
