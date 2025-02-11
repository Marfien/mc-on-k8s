package dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import dev.marfien.minecraftonk8s.common.AllocationStrategy;
import io.fabric8.crd.generator.annotation.SelectableField;
import io.fabric8.generator.annotation.Default;
import io.fabric8.generator.annotation.Min;
import io.fabric8.generator.annotation.Nullable;
import io.fabric8.generator.annotation.Pattern;
import io.fabric8.kubernetes.api.builder.Editable;
import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import io.fabric8.kubernetes.model.annotation.LabelSelector;
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

    @JsonPropertyDescription("The template for the underlying proxy pod")
    private PodTemplateSpec template;

    @Default("info")
    @Pattern("^(trace|debug|info|warn|error)$")
    @JsonPropertyDescription("The log level for the Agones SDK server")
    private String sdkServerLogLevel = "info";

    @Min(1)
    @Default("5")
    @JsonPropertyDescription("The interval in minutes to rebuild the informer cache. If you don't know what this is, don't change it.")
    private int cacheRebuildIntervalMinutes = 5;

    @Nullable
    @LabelSelector
    @JsonPropertyDescription("The label selector for filtering the Minecraft servers to add to the proxy")
    private String labelSelectorString;

    @Default("PLAYERS")
    @SelectableField
    @JsonPropertyDescription("The allocation strategy for the Minecraft server")
    private AllocationStrategy allocationStrategy = AllocationStrategy.PLAYERS;

    @JsonPropertyDescription("The drainage configuration for all the proxies in this fleet.")
    private DrainageSpec drainage;

    @Override
    public MinecraftProxySpecBuilder edit() {
        return new MinecraftProxySpecBuilder(this);
    }
}
