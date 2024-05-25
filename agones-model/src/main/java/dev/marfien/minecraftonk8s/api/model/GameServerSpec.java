package dev.marfien.minecraftonk8s.api.model;

import io.fabric8.kubernetes.api.model.PodTemplateSpec;
import java.util.List;
import java.util.Map;
import io.sundr.builder.annotations.BuildableReference;
import lombok.AllArgsConstructor;
import io.sundr.builder.annotations.Buildable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder", refs = {
        @BuildableReference(PodTemplateSpec.class)
})
public class GameServerSpec {

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

}
