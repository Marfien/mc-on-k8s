package dev.marfien.minecraftonk8s.client.common;

import agones.dev.sdk.Sdk.Empty;
import dev.marfien.minecraftonk8s.client.common.ClientInterface.ScheduledTask;
import dev.marfien.minecraftonk8s.client.common.config.ClientConfiguration;
import dev.marfien.minecraftonk8s.client.common.hook.PlayerConnectionHook;
import io.grpc.stub.StreamObserver;
import net.infumia.agones4j.Agones;
import java.util.UUID;

public abstract class ClientAgent<I extends ClientInterface, C extends ClientConfiguration> {

    protected final Agones agones = Agones.builder().withTarget().build();

    protected final I clientInterface;
    protected final C configuration;

    private ScheduledTask healthCheckTask;

    protected ClientAgent(I clientInterface, C configuration) {
        this.clientInterface = clientInterface;
        this.configuration = configuration;
    }

    public void startHealthCheck() {
        StreamObserver<Empty> healthCheckStream = this.agones.healthCheckStream();
        this.healthCheckTask = this.clientInterface.scheduleTask(
                () -> healthCheckStream.onNext(Empty.getDefaultInstance()), 0, 1000);
    }

    public void onStart() {
        startHealthCheck();
        this.agones.ready();

        switch (this.configuration.getAllocationStrategy()) {
            case ALWAYS -> this.agones.allocate();
            case PLAYERS -> this.clientInterface.addHook(new PlayerAllocationHook());
            default -> { /* manuel is not manged by agent */ }
        }
    }

    public void onStop() {
        if (this.healthCheckTask != null) {
            this.healthCheckTask.cancel();
        }

        this.agones.shutdown();
    }

    private class PlayerAllocationHook implements PlayerConnectionHook {

        @Override
        public void onPlayerConnected(UUID playerId) {
            if (clientInterface.getPlayerCount() == 1) {
                agones.allocate();
            }
        }

        @Override
        public void onPlayerDisconnected(UUID playerId) {
            if (clientInterface.getPlayerCount() == 0) {
                agones.ready();
            }
        }
    }

}
