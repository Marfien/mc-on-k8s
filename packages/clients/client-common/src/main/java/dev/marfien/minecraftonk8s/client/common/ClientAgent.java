package dev.marfien.minecraftonk8s.client.common;

import agones.dev.sdk.Sdk.Empty;
import dev.marfien.minecraftonk8s.client.common.ClientInterface.ScheduledTask;
import dev.marfien.minecraftonk8s.client.common.config.ClientConfiguration;
import io.grpc.stub.StreamObserver;
import net.infumia.agones4j.Agones;

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
        if (this.configuration.getAllocationStrategy() == AllocationStrategy.ALWAYS) {
            this.agones.allocate();
        }

        this.agones.ready();
    }

    public void onStop() {
        if (this.healthCheckTask != null) {
            this.healthCheckTask.cancel();
        }

        this.agones.shutdown();
    }

}
