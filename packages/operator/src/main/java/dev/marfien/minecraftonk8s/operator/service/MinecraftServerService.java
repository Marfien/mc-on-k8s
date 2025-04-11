package dev.marfien.minecraftonk8s.operator.service;

import dev.marfien.minecraftonk8s.agones.model.GameServer;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServer;

public interface MinecraftServerService {

    MinecraftServer patch(MinecraftServer minecraftServer, GameServer backingGameServer);

    MinecraftServer patchErrorStatus(MinecraftServer resource, Exception e);
}
