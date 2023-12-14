package dev.marfien.minecraftonk8s.gameserverbase.api;

import dev.marfien.agones.AgonesClient;
import dev.marfien.agones.observer.VoidStreamObserver;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameServerAgonesBridge {

  private static final int HEALTH_SEND_INTERVALL = Integer.parseInt(Objects.requireNonNullElse(System.getenv("HEALTH_SEND_INTERVALL"), String.valueOf(10)));

  private final AgonesClient agonesClient = AgonesClient.create();
  private final AtomicBoolean allocated = new AtomicBoolean(false);

  public void ready() {
    this.agonesClient.setReady();
    this.agonesClient.startHealthTask(HEALTH_SEND_INTERVALL);
  }

  protected void handlePlayerLogin() {
    if (this.allocated.get()) return;

    this.agonesClient.allocate(new AllocationObserver());
  }

  public void handleShutdown() {
    this.agonesClient.close();
  }

  public AgonesClient getAgonesClient() {
    return this.agonesClient;
  }

  public AtomicBoolean getAllocated() {
    return this.allocated;
  }

  private class AllocationObserver implements VoidStreamObserver {

    @Override
    public void onNext() {
      /* Nothing to do here */
    }

    @Override
    public void onError(Throwable t) {
      // TODO error handling
    }

    @Override
    public void onCompleted() {
      GameServerAgonesBridge.this.allocated.set(true);
    }
  }
}
