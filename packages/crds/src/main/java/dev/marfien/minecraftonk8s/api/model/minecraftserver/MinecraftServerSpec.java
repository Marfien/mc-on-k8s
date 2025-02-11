package dev.marfien.minecraftonk8s.api.model.minecraftserver;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import dev.marfien.minecraftonk8s.common.AllocationStrategy;
import io.fabric8.crd.generator.annotation.SelectableField;
import io.fabric8.generator.annotation.Default;
import io.fabric8.generator.annotation.Nullable;
import io.fabric8.generator.annotation.Pattern;
import io.fabric8.generator.annotation.Required;
import io.fabric8.kubernetes.api.builder.Editable;
import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder", refs = {
        @BuildableReference(PodTemplateSpec.class)
})
public class MinecraftServerSpec implements Editable<MinecraftServerSpecBuilder> {

    // TODO: what are tags for again?
    @Nullable
    @SelectableField
    @JsonPropertyDescription("The tags to apply to the Minecraft server")
    private Set<@Pattern("^[a-z0-9-_]+$") String> tags;

    @Required
    @JsonPropertyDescription("The underlying pod template for the Minecraft server")
    private PodTemplateSpec template;

    @Default("info")
    @Pattern("^(trace|debug|info|warn|error)$")
    @JsonPropertyDescription("The log level for the Agones SDK server")
    private String sdkServerLogLevel = "info";

    @Default("PLAYERS")
    @SelectableField
    @JsonPropertyDescription("The allocation strategy for the Minecraft server")
    private AllocationStrategy allocationStrategy = AllocationStrategy.PLAYERS;

    @Override
    public MinecraftServerSpecBuilder edit() {
        return new MinecraftServerSpecBuilder(this);
    }
}
