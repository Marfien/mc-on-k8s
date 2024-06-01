package dev.marfien.minecraftonk8s.api.model.minecraftserverfleet;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Plural;
import io.fabric8.kubernetes.model.annotation.ShortNames;
import io.fabric8.kubernetes.model.annotation.Version;
import io.sundr.builder.annotations.Buildable;

@Group("mconk8s.marfien.dev")
@Version("v1alpha1")
@Plural("minecraftserverfleets")
@ShortNames({"mcsf", "mcsfleet", "mcsfleets"})
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class MinecraftServerFleet
        extends CustomResource<MinecraftServerFleetSpec, MinecraftServerFleetStatus>
        implements Namespaced {
}
