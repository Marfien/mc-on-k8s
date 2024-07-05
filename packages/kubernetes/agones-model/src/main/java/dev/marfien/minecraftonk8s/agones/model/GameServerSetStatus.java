package dev.marfien.minecraftonk8s.agones.model;

import io.sundr.builder.annotations.Buildable;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class GameServerSetStatus {

    private int replicas;

    private int readyReplicas;

    private int reservedReplicas;

    private int allocatedReplicas;

    private int shutdownReplicas;

    private AggregatedPlayerStatus players;

    private Map<String, AggregatedCounterStatus> counters;

    private Map<String, AggregatedListStatus> lists;

}
