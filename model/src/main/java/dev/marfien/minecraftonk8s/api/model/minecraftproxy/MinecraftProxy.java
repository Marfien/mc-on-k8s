package dev.marfien.minecraftonk8s.api.model.minecraftproxy;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Plural;
import io.fabric8.kubernetes.model.annotation.ShortNames;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("mconk8s.marfien.dev")
@Version("v1alpha1")
@Plural("minecraftproxy")
@ShortNames({"mcp", "mcproxy", "mcproxies"})
public class MinecraftProxy extends CustomResource<MinecraftProxySpec, MinecraftProxyStatus> implements
        Namespaced {

}
