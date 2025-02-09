package dev.marfien.minecraftonk8s.agones.model;

import io.sundr.builder.annotations.Buildable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class GameServerSetSpec {

    private int replicas;

    private AllocationOverflow allocationOverflow;

    private String scheduling;

    private List<Priority> priorities;

    private GameServerTemplateSpec template;

}
