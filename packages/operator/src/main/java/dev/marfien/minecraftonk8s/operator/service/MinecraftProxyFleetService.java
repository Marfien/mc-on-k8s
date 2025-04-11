package dev.marfien.minecraftonk8s.operator.service;

import dev.marfien.minecraftonk8s.agones.model.Fleet;
import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.MinecraftProxyFleet;

public interface MinecraftProxyFleetService {

    MinecraftProxyFleet patchStatus(MinecraftProxyFleet fleet, Fleet backedFleet);

    MinecraftProxyFleet patchErrorStatus(MinecraftProxyFleet resource, Exception e);
}
