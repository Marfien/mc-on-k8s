package dev.marfien.minecraftonk8s.operator.service;

import dev.marfien.minecraftonk8s.agones.model.GameServer;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServer;
import dev.marfien.minecraftonk8s.operator.application.BinariesConfigMapEnforcer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.Set;

@ApplicationScoped
public class MinecraftServerServiceImpl implements MinecraftServerService {

    private static final Collection<String> READY_STATES = Set.of("Ready", "Reserved", "Allocated");

    @Inject
    BinariesConfigMapEnforcer binariesConfigMapEnforcer;

    @Inject
    ConditionService conditionService;

    @Override
    public MinecraftServer patch(MinecraftServer minecraftServer, GameServer backedGameServer) {
        this.binariesConfigMapEnforcer.ensureAgentBinary();

        String backedGameServerStatus = backedGameServer.getStatus().getState();

        boolean ready = minecraftServer.getStatus().isReady() || READY_STATES.contains(backedGameServerStatus);

        return minecraftServer.edit()
                .editStatus()
                    .withIp(backedGameServer.getStatus().getAddress())
                    // TODO: This should be the actual port of the game server
                    .withPort(25565)
                    .withReady(ready)
                    .addToConditions(this.conditionService.ready("GameServerReady", "The backing agones game server is ready"))
                    .endStatus()
                .build();
    }

    @Override
    public MinecraftServer patchErrorStatus(MinecraftServer resource, Exception e) {
        return resource.edit()
                .editStatus()
                    .addToConditions(this.conditionService.fromException(e))
                    .endStatus()
                .build();
    }
}
