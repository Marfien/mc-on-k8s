package dev.marfien.minecraftonk8s.api.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import io.sundr.builder.annotations.Buildable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
@NoArgsConstructor
@AllArgsConstructor
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
