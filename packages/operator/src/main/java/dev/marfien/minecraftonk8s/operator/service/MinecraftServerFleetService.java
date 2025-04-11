package dev.marfien.minecraftonk8s.operator.service;

import dev.marfien.minecraftonk8s.agones.model.Fleet;
import dev.marfien.minecraftonk8s.api.model.minecraftserverfleet.MinecraftServerFleet;

public interface MinecraftServerFleetService {

    MinecraftServerFleet patch(MinecraftServerFleet fleet, Fleet backedFleet);

    MinecraftServerFleet patchErrorStatus(MinecraftServerFleet resource, Exception e);
}
