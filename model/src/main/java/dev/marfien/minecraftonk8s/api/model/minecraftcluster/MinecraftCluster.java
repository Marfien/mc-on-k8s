package dev.marfien.minecraftonk8s.api.model.minecraftcluster;

import dev.marfien.minecraftonk8s.api.model.minecraftcluster.MinecraftCluster.MinecraftClusterSpec;
import dev.marfien.minecraftonk8s.api.model.minecraftcluster.MinecraftCluster.MinecraftClusterStatus;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Plural;
import io.fabric8.kubernetes.model.annotation.ShortNames;
import io.fabric8.kubernetes.model.annotation.Version;


@Group("mconk8s.marfien.dev")
@Version("v1alpha1")
@Plural("minecraftclusters")
@ShortNames({"mcc", "mccluster", "mcclusters"})
public class MinecraftCluster extends CustomResource<MinecraftClusterSpec, MinecraftClusterStatus> {

    public static class MinecraftClusterSpec {}
    public static class MinecraftClusterStatus {}

}
