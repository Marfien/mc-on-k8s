package dev.marfien.minecraftonk8s.operator.minecraftproxyfleet.dependentresource;

import dev.marfien.minecraftonk8s.api.model.Fleet;
import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.MinecraftProxyFleet;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;

public class FleetDependentResource extends CRUDKubernetesDependentResource<Fleet, MinecraftProxyFleet> {

    public FleetDependentResource() {
        super(Fleet.class);
    }

    @Override
    protected Fleet desired(MinecraftProxyFleet primary, Context<MinecraftProxyFleet> context) {

    }
}
