package dev.marfien.minecraftonk8s.client.api;

import agones.dev.sdk.Sdk.Empty;
import agones.dev.sdk.Sdk.GameServer.ObjectMeta;
import dev.marfien.minecraftonk8s.client.api.ClientInterface.ScheduledTask;
import dev.marfien.minecraftonk8s.client.api.adapter.KubernetesAdapter;
import dev.marfien.minecraftonk8s.client.api.config.ClientConfiguration;
import dev.marfien.minecraftonk8s.client.api.hook.PlayerDisconnectHook;
import dev.marfien.minecraftonk8s.client.api.hook.PostPlayerConnectHook;
import io.grpc.stub.StreamObserver;
import java.time.Duration;
import net.infumia.agones4j.Agones;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ClientAgent<I extends ClientInterface, C extends ClientConfiguration> implements ClientAPI {

    protected final Logger logger = LoggerFactory.getLogger("ClientAgent");

    protected final Agones agones = Agones.builder().withTarget().build();

    protected final I clientInterface;
    protected final C configuration;

    private ScheduledTask healthCheckTask;
    private KubernetesAdapter kubernetesAdapter;

    protected ClientAgent(I clientInterface, C configuration) {
        this.clientInterface = clientInterface;
        this.configuration = configuration;

        ClientAPI.InstanceHolder.set(this);
    }

    public void startHealthCheck() {
        StreamObserver<Empty> healthCheckStream = this.agones.healthCheckStream();
        this.healthCheckTask = this.clientInterface.scheduleTask(
                () -> healthCheckStream.onNext(Empty.getDefaultInstance()), 0, 1000);
    }

    public void onStartup() {
        try {
            this.startHealthCheck();
            this.agones.ready();
            // Create async k8s adapter
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

            AllocationStrategy allocationStrategy = this.configuration.getAllocationStrategy();
            switch (allocationStrategy) {
                case ALWAYS: this.agones.allocate(); break;
                case PLAYERS: this.handlePlayerAllocationStrategy(); break;
                case MANUAL: { /* manuel is not manged by agent */ } break;
                default: throw new UnsupportedOperationException(String.format("Unsupported allocation strategy: %s", allocationStrategy));
            }
        } catch (Exception e) {
            this.logger.error("Failed to initialize agent. Stopping", e);
            this.shutdown();
        }
    }

    public void onShutdown() {
        if (this.healthCheckTask != null) {
            this.healthCheckTask.cancel();
        }
    }

    public void shutdown() {
        try {
            this.logger.info("Requesting graceful shutdown from agones");
            this.agones.shutdown();
        } catch (Exception e) {
            this.logger.error("Failed to request shutdown from agones. Shutting down manually", e);
            System.exit(0);
        }
    }

    @Override
    public void allocate() {
        this.logger.debug("Allocating game server");
        this.agones.allocate();
    }

    @Override
    public void reserve(Duration duration) {
        this.logger.debug("Reserving game server for {}", duration);
        this.agones.reserve(duration);
    }

    @Override
    public void ready() {
        this.logger.debug("Marking game server as ready");
        this.agones.ready();
    }

    @Override
    public void requestShutdown() {
        this.logger.debug("Requesting shutdown from agones");
        this.agones.shutdown();
    }

    public KubernetesAdapter getKubernetesAdapter() {
        return this.kubernetesAdapter;
    }

    private void handlePlayerAllocationStrategy() {
        this.clientInterface.addHook((PostPlayerConnectHook) event -> {
            if (clientInterface.getPlayerCount() == 1) {
                this.allocate();
            }
        });
        this.clientInterface.addHook((PlayerDisconnectHook) event -> {
            clientInterface.scheduleTask(() -> {
                if (clientInterface.getPlayerCount() == 0) {
                    agones.ready();
                }
            }, 1);
        });
    }

}
