package dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.fabric8.generator.annotation.Default;
import io.fabric8.generator.annotation.Min;
import io.fabric8.kubernetes.api.builder.Editable;
import io.sundr.builder.annotations.Buildable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class DrainageSpec implements Editable<DrainageSpecBuilder> {

    @Min(1)
    @Default("8")
    @JsonPropertyDescription("The duration to wait before draining the pod.")
    private int delayHours = 8;

    @Min(1)
    @Default("12")
    @JsonPropertyDescription("The duration to wait for before players are forcefully kicked.")
    private int timeoutHours = 12;

    @Override
    public DrainageSpecBuilder edit() {
        return new MinecraftProxyFleetSpecBuilder(this);
    }
}
