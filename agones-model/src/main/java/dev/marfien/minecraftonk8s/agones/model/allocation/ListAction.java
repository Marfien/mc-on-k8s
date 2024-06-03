package dev.marfien.minecraftonk8s.agones.model.allocation;

import io.sundr.builder.annotations.Buildable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class ListAction {

    private List<String> addValues;

    private long capacity;

}
