package dev.marfien.minecraftonk8s.api.model.minecraftproxy;

import io.fabric8.kubernetes.api.builder.Editable;
import io.sundr.builder.annotations.Buildable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class MinecraftProxyStatus implements Editable<MinecraftProxyStatusBuilder> {

    private Status status;

    @Override
    public MinecraftProxyStatusBuilder edit() {
        return new MinecraftProxyStatusBuilder(this);
    }

    public enum Status {
        STARTING,
        ACTIVE,
        DRAINING,
        STOPPING
    }

}
