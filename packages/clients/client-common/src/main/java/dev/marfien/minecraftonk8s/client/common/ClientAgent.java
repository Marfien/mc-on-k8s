package dev.marfien.minecraftonk8s.client.common;

import agones.dev.sdk.Sdk.Empty;
import agones.dev.sdk.Sdk.GameServer.ObjectMeta;
import dev.marfien.minecraftonk8s.client.common.ClientInterface.ScheduledTask;
import dev.marfien.minecraftonk8s.client.common.adapter.KubernetesAdapter;
import dev.marfien.minecraftonk8s.client.common.config.ClientConfiguration;
import dev.marfien.minecraftonk8s.client.common.hook.PlayerConnectionHook;
import io.grpc.stub.StreamObserver;
import java.time.Duration;
import java.util.UUID;
import net.infumia.agones4j.Agones;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ClientAgent<I extends ClientInterface, C extends ClientConfiguration> {

    protected final Logger logger = LoggerFactory.getLogger("ClientAgent");

    protected final Agones agones = Agones.builder().withTarget().build();

    protected final I clientInterface;
    protected final C configuration;

    private ScheduledTask healthCheckTask;
    private KubernetesAdapter kubernetesAdapter;

    protected ClientAgent(I clientInterface, C configuration) {
        this.clientInterface = clientInterface;
        this.configuration = configuration;
    }

    public void startHealthCheck() {
        StreamObserver<Empty> healthCheckStream = this.agones.healthCheckStream();
        this.healthCheckTask = this.clientInterface.scheduleTask(
                () -> healthCheckStream.onNext(Empty.getDefaultInstance()), 0, 1000);
    }

    public void onStartup() {
        try {
            startHealthCheck();
            this.agones.ready();
            this.agones.getGameServerFuture().thenAccept(gameServer -> {
                        ObjectMeta meta = gameServer.getObjectMeta();
                        this.kubernetesAdapter = new KubernetesAdapter(meta.getName(), meta.getNamespace());
                        this.logger.info(
                                "Found backing agones game server: {}/{}",
                                this.kubernetesAdapter.getNamespace(),
                                this.kubernetesAdapter.getName()
                        );
                    }
            );

            switch (this.configuration.getAllocationStrategy()) {
                case ALWAYS -> this.agones.allocate();
                case PLAYERS -> this.clientInterface.addHook(new PlayerAllocationHook());
                default -> { /* manuel is not manged by agent */ }
            }
        } catch (Exception e) {
            this.logger.error("Failed to initialize agent. Stopping", e);
            shutdown();
        }
    }

    public void onShutdown() {
        if (this.healthCheckTask != null) {
            this.healthCheckTask.cancel();
        }
    }

    public void shutdown() {
        try {
            this.agones.shutdown();
        } catch (Exception e) {
            this.logger.error("Failed to request shutdown from agones. Shutting down manually", e);
            System.exit(0);
        }
    }

    public void allocate() {
        this.agones.allocate();
    }

    public void reserve(int seconds) {
        this.agones.reserve(Duration.ofSeconds(seconds));
    }

    public void requestShutdown() {
        this.agones.shutdown();
    }

    public KubernetesAdapter getKubernetesAdapter() {
        return this.kubernetesAdapter;
    }

    private class PlayerAllocationHook implements PlayerConnectionHook {

        @Override
        public void onPlayerConnected(UUID playerId) {
            if (clientInterface.getPlayerCount() == 1) {
                allocate();
            }
        }

        @Override
        public void onPlayerDisconnected(UUID playerId) {
            clientInterface.scheduleTask(() -> {
                if (clientInterface.getPlayerCount() == 0) {
                    agones.ready();
                }
            }, 1);
        }
    }

}
