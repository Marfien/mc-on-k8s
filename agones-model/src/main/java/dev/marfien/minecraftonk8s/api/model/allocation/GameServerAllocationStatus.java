package dev.marfien.minecraftonk8s.api.model.allocation;

import dev.marfien.minecraftonk8s.api.model.GameServerStatusPort;
import io.fabric8.kubernetes.api.model.NodeAddress;
import java.util.List;
import io.sundr.builder.annotations.BuildableReference;
import lombok.AllArgsConstructor;
import io.sundr.builder.annotations.Buildable;
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
