package dev.marfien.minecraftonk8s.api.model;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.sundr.builder.annotations.Buildable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
@NoArgsConstructor
@AllArgsConstructor
public class GameServerTemplateSpec {

    private ObjectMeta metadata;

    private GameServerSpec spec;

}
