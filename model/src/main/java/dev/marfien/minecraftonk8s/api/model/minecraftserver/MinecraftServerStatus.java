package dev.marfien.minecraftonk8s.api.model.minecraftserver;

import io.fabric8.kubernetes.api.builder.Editable;
import io.sundr.builder.annotations.Buildable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class MinecraftServerStatus implements Editable<MinecraftServerStatusBuilder> {

    private boolean ready;
    private String ip;
    private int port;

    private String errorMessage;

    @Override
    public MinecraftServerStatusBuilder edit() {
        return new MinecraftServerStatusBuilder(this);
    }
}
