package dev.marfien.minecraftonk8s.api.model.minecraftcluster;

import dev.marfien.minecraftonk8s.api.model.minecraftcluster.MinecraftCluster.MinecraftClusterSpec;
import dev.marfien.minecraftonk8s.api.model.minecraftcluster.MinecraftCluster.MinecraftClusterStatus;
import dev.marfien.minecraftonk8s.common.Constants;
import io.fabric8.kubernetes.api.builder.Editable;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Plural;
import io.fabric8.kubernetes.model.annotation.ShortNames;
import io.fabric8.kubernetes.model.annotation.Version;
import io.sundr.builder.annotations.Buildable;

@Group(Constants.GROUP)
@Version(Constants.VERSION)
@Plural("minecraftclusters")
@ShortNames({"mcc", "mccluster", "mcclusters"})
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder")
public class MinecraftCluster extends
        CustomResource<MinecraftClusterSpec, MinecraftClusterStatus> implements
        Editable<MinecraftClusterBuilder> {

    @Override
    public MinecraftClusterBuilder edit() {
        return new MinecraftClusterBuilder(this);
    }

    public static class MinecraftClusterSpec {

    }

    public static class MinecraftClusterStatus {

    }

}
