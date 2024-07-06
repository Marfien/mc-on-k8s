package dev.marfien.minecraftonk8s.client.common;

import agones.dev.sdk.Sdk.Empty;
import dev.marfien.minecraftonk8s.client.common.ClientInterface.ScheduledTask;
import io.grpc.stub.StreamObserver;
import net.infumia.agones4j.Agones;

public abstract class ClientAgent<C extends ClientInterface> {

    protected final C clientInterface;
    protected final Agones agones = Agones.builder().withTarget().build();
    private ScheduledTask healthCheckTask;

    protected ClientAgent(C clientInterface) {
        this.clientInterface = clientInterface;
    }

    public void startHealthCheck() {
        StreamObserver<Empty> healthCheckStream = this.agones.healthCheckStream();
        this.healthCheckTask = this.clientInterface.scheduleTask(
                () -> healthCheckStream.onNext(Empty.getDefaultInstance()), 0, 1000);
    }

    public void onStop() {
        if (this.healthCheckTask != null) {
            this.healthCheckTask.cancel();
        }
    }

}
