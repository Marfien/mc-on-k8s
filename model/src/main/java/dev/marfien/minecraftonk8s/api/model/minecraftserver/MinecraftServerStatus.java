package dev.marfien.minecraftonk8s.api.model.minecraftserver;

import io.fabric8.kubernetes.api.builder.Editable;
import io.fabric8.kubernetes.api.model.Condition;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;
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
        }
)
public class MinecraftServerStatus implements Editable<MinecraftServerStatusBuilder> {

    private boolean ready;
    private String ip;
    private int port;

    private List<Condition> conditions;

    @Override
    public MinecraftServerStatusBuilder edit() {
        return new MinecraftServerStatusBuilder(this);
    }
}
