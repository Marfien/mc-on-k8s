package dev.marfien.minecraftonk8s.api.model;

import io.fabric8.kubernetes.api.model.NodeAddress;
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
        @BuildableReference(NodeAddress.class)
})
public class GameServerStatus {

    private String state;

    private List<GameServerStatusPort> ports;

    private String address;

    private List<NodeAddress> addresses;

    private String nodeName;

    private String reservedUntil;

    private PlayerStatus players;

    private Map<String, CounterStatus> counters;

    private Map<String, ListStatus> lists;

    private Eviction eviction;
}
