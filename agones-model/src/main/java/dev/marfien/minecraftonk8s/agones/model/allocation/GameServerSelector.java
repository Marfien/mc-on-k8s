package dev.marfien.minecraftonk8s.agones.model.allocation;

import io.fabric8.kubernetes.api.model.LabelSelector;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder", refs = {
        @BuildableReference(LabelSelector.class)
})
public class GameServerSelector {

    private LabelSelector labelSelector;

    private String gameServerState;

    private PlayerSelector players;

    private Map<String, CounterSelector> counters;

    private Map<String, ListSelector> lists;

}
