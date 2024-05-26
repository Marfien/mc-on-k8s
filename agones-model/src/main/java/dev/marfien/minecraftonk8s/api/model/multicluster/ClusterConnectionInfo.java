package dev.marfien.minecraftonk8s.api.model.multicluster;

import io.sundr.builder.annotations.Buildable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
@NoArgsConstructor
@AllArgsConstructor
public class ClusterConnectionInfo {

    private String clusterName;

    private List<String> allocationEndpoints;

    private String secretName;

    private String namespace;

    private byte[] serverCa;

}
