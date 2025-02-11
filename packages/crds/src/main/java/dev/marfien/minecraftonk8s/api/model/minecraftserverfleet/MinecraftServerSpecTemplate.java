package dev.marfien.minecraftonk8s.api.model.minecraftserverfleet;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServerSpec;
import io.fabric8.generator.annotation.Required;
import io.fabric8.kubernetes.api.builder.Editable;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.sundr.builder.annotations.Buildable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class MinecraftServerSpecTemplate implements Editable<MinecraftServerSpecTemplateBuilder> {

    @Required
    @JsonPropertyDescription("The metadata for the Minecraft server.")
    private ObjectMeta metadata;

    @Required
    @JsonPropertyDescription("The spec for the Minecraft server.")
    private MinecraftServerSpec spec;

    @Override
    public MinecraftServerSpecTemplateBuilder edit() {
        return new MinecraftServerSpecTemplateBuilder(this);
    }
}
