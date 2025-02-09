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
public class PlayerStatus {

    private long count;

    private long capacity;

    private List<String> ids;

}
