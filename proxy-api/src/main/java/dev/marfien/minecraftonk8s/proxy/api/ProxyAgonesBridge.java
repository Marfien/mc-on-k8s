package dev.marfien.minecraftonk8s.proxy.api;

import dev.marfien.agones.kubernetes.api.model.GameServer;
import dev.marfien.minecraftonk8s.gameserverbase.api.GameServerAgonesBridge;
import io.fabric8.kubernetes.api.model.KubernetesResourceList;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import net.kyori.adventure.text.Component;

public abstract class ProxyAgonesBridge extends GameServerAgonesBridge {

  private SharedIndexInformer<GameServer> informer;

  private boolean shuttingDown = false;

  @Override
  public final void ready() {
    super.ready();
    this.schedule(Duration.of(12, ChronoUnit.HOURS), this::shuttingDown);
    try (KubernetesClient client = new KubernetesClientBuilder().build()) {
      MixedOperation<GameServer, KubernetesResourceList<GameServer>, Resource<GameServer>>
          operation = client.resources(GameServer.class);

      // TODO blocking?
      this.informer =
          operation
              .inAnyNamespace()
              .runnableInformer(5)
              .addEventHandler(new GameServerEventHandler(this));
      this.informer.start();
    }
  }

  protected final Optional<Component> handleLogin() {
    super.handlePlayerLogin();

    return this.shuttingDown
        ? Optional.of(
            Component.text(
                "This proxy is already shutting down and is waiting for all players to leave."))
        : Optional.empty();
  }

  @Override
  public final void handleShutdown() {
    super.handleShutdown();
    this.informer.close();
  }

  private void shuttingDown() {
    this.shuttingDown = true;
    super.getAgonesClient().setLable("@project.group@/shutting-down", "true");
  }

  protected abstract void schedule(Duration delay, Runnable task);

  protected abstract void addServer(String name, InetSocketAddress address);

  protected abstract void removeServer(String name, InetSocketAddress address);
}
