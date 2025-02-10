package dev.marfien.minecraftonk8s.api.model.minecraftserverfleet;

import dev.marfien.minecraftonk8s.common.Constant;
import io.fabric8.kubernetes.api.builder.Editable;
import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Plural;
import io.fabric8.kubernetes.model.annotation.ShortNames;
import io.fabric8.kubernetes.model.annotation.Version;
import io.sundr.builder.annotations.Buildable;

@Group(Constant.CRDS_GROUP)
@Version(Constant.CRDS_VERSION)
@Plural(Constant.MinecraftServerFleet.PLURAL)
@ShortNames(Constant.MinecraftServerFleet.SHORT_NAME)
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class MinecraftServerFleet
        extends CustomResource<MinecraftServerFleetSpec, MinecraftServerFleetStatus>
        implements Namespaced, Editable<MinecraftServerFleetBuilder> {

    @Override
    public MinecraftServerFleetBuilder edit() {
        return new MinecraftServerFleetBuilder(this);
    }
}
