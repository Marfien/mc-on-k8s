package dev.marfien.minecraftonk8s.api.model.minecraftserverfleet;

import io.fabric8.kubernetes.api.builder.Editable;
import io.sundr.builder.annotations.Buildable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class MinecraftServerFleetStatus implements Editable<MinecraftServerFleetStatusBuilder> {

    private int replicas;
    private int readyReplicas;
    private int allocatedReplicas;

    private String errorMessage;

    @Override
    public MinecraftServerFleetStatusBuilder edit() {
        return new MinecraftServerFleetStatusBuilder(this);
    }
}
