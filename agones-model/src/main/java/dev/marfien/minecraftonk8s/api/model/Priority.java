package dev.marfien.minecraftonk8s.api.model;

import lombok.AllArgsConstructor;
import io.sundr.builder.annotations.Buildable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
@NoArgsConstructor
@AllArgsConstructor
public class Priority {

    private String type;

    private String key;

    private String order;

}
