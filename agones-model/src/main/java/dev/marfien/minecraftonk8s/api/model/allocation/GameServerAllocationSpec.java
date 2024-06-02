package dev.marfien.minecraftonk8s.api.model.allocation;

import dev.marfien.minecraftonk8s.api.model.Priority;
import io.sundr.builder.annotations.Buildable;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class GameServerAllocationSpec {

    private MultiClusterSetting multiClusterSetting;

    private GameServerSelector required;

    private List<GameServerSelector> preferred;

    private List<Priority> priorities;

    private List<GameServerSelector> selectors;

    private String scheduling;

    private MetaPatch metadata;

    private Map<String, CounterAction> counters;

    private Map<String, ListAction> lists;

}
