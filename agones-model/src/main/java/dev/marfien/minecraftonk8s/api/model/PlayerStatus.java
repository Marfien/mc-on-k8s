package dev.marfien.minecraftonk8s.api.model;

import java.util.List;
import lombok.AllArgsConstructor;
import io.sundr.builder.annotations.Buildable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatus {

    private long count;

    private long capacity;

    private List<String> ids;

}
