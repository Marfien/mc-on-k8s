package dev.marfien.minecraftonk8s.api.model.minecraftserver;

import dev.marfien.minecraftonk8s.api.model.Constants;
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
@Plural("minecraftservers")
@ShortNames({"mcs", "mcserver", "mcservers"})
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class MinecraftServer extends
        CustomResource<MinecraftServerSpec, MinecraftServerStatus> implements
        Namespaced, Editable<MinecraftServerBuilder> {

    @Override
    public MinecraftServerBuilder edit() {
        return new MinecraftServerBuilder(this);
    }
}
