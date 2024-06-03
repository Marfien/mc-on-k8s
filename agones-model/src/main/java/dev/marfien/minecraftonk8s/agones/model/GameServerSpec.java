package dev.marfien.minecraftonk8s.agones.model;

import dev.marfien.minecraftonk8s.api.model.GameServerSpecBuilder;
import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import io.sundr.builder.Editable;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder", refs = {
        @BuildableReference(PodTemplateSpec.class)
})
public class GameServerSpec implements Editable<GameServerSpecBuilder> {

    private String container;

    private List<GameServerPort> ports;

    private Health health;

    private String scheduling;

    private SdkServer sdkServer;

    private PodTemplateSpec template;

    private PlayerSpec players;

    private Map<String, CounterStatus> counters;

    private Map<String, ListStatus> lists;

    private Eviction eviction;

    @Override
    public GameServerSpecBuilder edit() {
        return new GameServerSpecBuilder(this);
    }
}
