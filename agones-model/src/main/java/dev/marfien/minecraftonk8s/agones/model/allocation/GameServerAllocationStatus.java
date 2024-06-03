package dev.marfien.minecraftonk8s.agones.model.allocation;

import dev.marfien.minecraftonk8s.agones.model.GameServerStatusPort;
import io.fabric8.kubernetes.api.model.NodeAddress;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder", refs = {
        @BuildableReference(NodeAddress.class)
})
public class GameServerAllocationStatus {

    private String state;

    private String gameServerName;

    private List<GameServerStatusPort> ports;

    private String address;

    private List<NodeAddress> addresses;

    private String nodeName;

    private String source;

    private GameServerMetadata metadata;

}
