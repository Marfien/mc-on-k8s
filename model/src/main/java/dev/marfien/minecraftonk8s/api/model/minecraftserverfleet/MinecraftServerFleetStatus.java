package dev.marfien.minecraftonk8s.api.model.minecraftserverfleet;

import io.fabric8.kubernetes.api.builder.Editable;
import io.fabric8.kubernetes.api.model.Condition;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(
        editableEnabled = false,
        lazyCollectionInitEnabled = false,
        builderPackage = "io.fabric8.kubernetes.api.builder",
        refs = {
        @BuildableReference(Condition.class)
})
public class MinecraftServerFleetStatus implements Editable<MinecraftServerFleetStatusBuilder> {

    private int replicas;
    private int readyReplicas;
    private int allocatedReplicas;

    private List<Condition> conditions = new ArrayList<>();

    @Override
    public MinecraftServerFleetStatusBuilder edit() {
        return new MinecraftServerFleetStatusBuilder(this);
    }
}
