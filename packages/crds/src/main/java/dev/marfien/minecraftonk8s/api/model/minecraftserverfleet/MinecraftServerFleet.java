package dev.marfien.minecraftonk8s.api.model.minecraftserverfleet;

import dev.marfien.minecraftonk8s.common.Constants;
import io.fabric8.kubernetes.api.builder.Editable;
import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Plural;
import io.fabric8.kubernetes.model.annotation.ShortNames;
import io.fabric8.kubernetes.model.annotation.Version;
import io.sundr.builder.annotations.Buildable;

@Group(Constants.GROUP)
@Version(Constants.VERSION)
@Plural("minecraftserverfleets")
@ShortNames({"mcsf", "mcsfleet", "mcsfleets"})
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class MinecraftServerFleet
        extends CustomResource<MinecraftServerFleetSpec, MinecraftServerFleetStatus>
        implements Namespaced, Editable<MinecraftServerFleetBuilder> {

    @Override
    public MinecraftServerFleetBuilder edit() {
        return new MinecraftServerFleetBuilder(this);
    }
}
