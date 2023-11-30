package dev.marfien.agones;

import com.google.protobuf.FieldMask;
import dev.agones.sdk.GameServer;
import dev.agones.sdk.allocation.AllocationRequest;
import dev.agones.sdk.allocation.AllocationResponse;
import dev.agones.sdk.alpha.Count;
import dev.marfien.agones.internal.AgonesAlphaSdk;
import dev.marfien.agones.internal.AgonesBetaSdk;
import dev.marfien.agones.internal.AgonesSdk;
import dev.marfien.agones.model.CounterStore;
import dev.marfien.agones.model.ListStore;
import dev.marfien.agones.observer.BooleanStreamObserver;
import dev.marfien.agones.observer.LongStreamObserver;
import dev.marfien.agones.observer.StreamObserverUtil;
import dev.marfien.agones.observer.VoidStreamObserver;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.time.Duration;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class AgonesClient implements AutoCloseable {

  private final ManagedChannel channel;

  private final int shutdownTimeout;

  private final AgonesAlphaSdk alphaSdk;

  private final AgonesBetaSdk betaSdk;

  private final AgonesSdk sdk;

  private Timer timer;

  private AgonesClient(ManagedChannel channel, int shutdownTimeout) {
    this.channel = channel;
    this.shutdownTimeout = shutdownTimeout;
    this.alphaSdk = new AgonesAlphaSdk(channel);
    this.betaSdk = new AgonesBetaSdk(channel);
    this.sdk = new AgonesSdk(channel);
  }

  @Override
  public void close() {
    try {
      this.channel.shutdown().awaitTermination(this.shutdownTimeout, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      this.channel.shutdownNow();
    }

    if (this.timer != null) {
      this.timer.cancel();
    }
  }

  public void allocate() {
    allocate(VoidStreamObserver.NO_OP);
  }

  public void allocate(VoidStreamObserver observer) {
    this.sdk.allocateSelf(observer.toSdk());
  }

  public void allocate(AllocationRequest request, StreamObserver<AllocationResponse> observer) {
    this.sdk.allocate(request, observer);
  }

  public void getGameServer(StreamObserver<GameServer> observer) {
    this.sdk.getGameServer(observer);
  }

  public void sendHealth() {
    sendHealth(VoidStreamObserver.NO_OP);
  }

  public void sendHealth(VoidStreamObserver observer) {
    this.sdk.health(observer.toSdk());
  }

  public void startHealthTask(int delaySeconds) {
    if (this.timer != null) {
      throw new IllegalStateException("HealthTask already started");
    }

    this.timer = new Timer(true);
    timer.scheduleAtFixedRate(
        new TimerTask() {
          @Override
          public void run() {
            AgonesClient.this.sendHealth();
          }
        },
        0,
        delaySeconds);
  }

  public void setReady() {
    setReady(VoidStreamObserver.NO_OP);
  }

  public void setReady(VoidStreamObserver observer) {
    this.sdk.ready(observer.toSdk());
  }

  public void setReserved(Duration duration) {
    setReserved(duration, VoidStreamObserver.NO_OP);
  }

  public void setReserved(Duration duration, VoidStreamObserver observer) {
    this.sdk.reserve(
        dev.agones.sdk.Duration.newBuilder().setSeconds(duration.toSeconds()).build(),
        observer.toSdk());
  }

  public void setAnnotation(String key, String value) {
    setAnnotation(key, value, VoidStreamObserver.NO_OP);
  }

  public void setAnnotation(String key, String value, VoidStreamObserver observer) {
    this.sdk.setAnnotation(key, value, observer.toSdk());
  }

  public void setLable(String key, String value) {
    setLable(key, value, VoidStreamObserver.NO_OP);
  }

  public void setLable(String key, String value, VoidStreamObserver observer) {
    this.sdk.setLabel(key, value, observer.toSdk());
  }

  public void setShutdown() {
    setShutdown(VoidStreamObserver.NO_OP);
  }

  public void setShutdown(VoidStreamObserver observer) {
    this.sdk.shutdown(observer.toSdk());
  }

  public void watchGameServer(StreamObserver<GameServer> observer) {
    this.sdk.watchGameServer(observer);
  }

  public void addToList(String listName, String value, StreamObserver<ListStore> observer) {
    this.alphaSdk.addList(listName, value, StreamObserverUtil.listStoreToAgonesList(observer));
  }

  public void removeFromList(String listName, String value, StreamObserver<ListStore> observer) {
    this.alphaSdk.removeList(listName, value, StreamObserverUtil.listStoreToAgonesList(observer));
  }

  public void getList(String listName, StreamObserver<ListStore> observer) {
    this.alphaSdk.getList(listName, StreamObserverUtil.listStoreToAgonesList(observer));
  }

  public void updateList(ListStore list, FieldMask updateMask, StreamObserver<ListStore> observer) {
    this.alphaSdk.updateList(
        list.toAgonesList(), updateMask, StreamObserverUtil.listStoreToAgonesList(observer));
  }

  public void getConnectedPlayers(StreamObserver<java.util.List<String>> observer) {
    this.alphaSdk.getConnectedPlayers(StreamObserverUtil.listToAlphaSdkIdList(observer));
  }

  public void getCounter(String counterName, StreamObserver<CounterStore> observer) {
    this.alphaSdk.getCounter(counterName, StreamObserverUtil.counterStoreToAgonesList(observer));
  }

  public void updateCounter(
      CounterStore counter, FieldMask updateMask, StreamObserver<CounterStore> observer) {
    this.alphaSdk.updateCounter(
        counter.toAgonesCounter(),
        updateMask,
        StreamObserverUtil.counterStoreToAgonesList(observer));
  }

  public void getPlayerCapacity(LongStreamObserver observer) {
    this.alphaSdk.getPlayerCapacity(observer.toCountObserver());
  }

  public void setPlayerCapacity(long capacity) {
    setPlayerCapacity(capacity, VoidStreamObserver.NO_OP);
  }

  public void setPlayerCapacity(long capacity, VoidStreamObserver observer) {
    this.alphaSdk.setPlayerCapacity(
        Count.newBuilder().setCount(capacity).build(), observer.toAlpha());
  }

  public void getPlayerCount(LongStreamObserver observer) {
    this.alphaSdk.getPlayerCount(observer.toCountObserver());
  }

  public void isPlayerConnected(String playerId, BooleanStreamObserver observer) {
    this.alphaSdk.isPlayerConnected(playerId, observer.toAlphaObserver());
  }

  public void playerConnect(String playerId, BooleanStreamObserver observer) {
    this.alphaSdk.playerConnect(playerId, observer.toAlphaObserver());
  }

  public void playerDisconnect(String playerId, BooleanStreamObserver observer) {
    this.alphaSdk.playerDisconnect(playerId, observer.toAlphaObserver());
  }

  public AgonesSdk getSdk() {
    return this.sdk;
  }

  public AgonesBetaSdk getBetaSdk() {
    return this.betaSdk;
  }

  public AgonesAlphaSdk getAlphaSdk() {
    return this.alphaSdk;
  }

  public static final String AGONES_SDK_GRPC_HOST =
      Objects.requireNonNullElse(System.getenv("AGONES_SDK_GRPC_HOST"), "localhost");
  public static final int AGONES_SDK_GRPC_PORT = readPortFromEnv("AGONES_SDK_GRPC_PORT", 9357);

  public static final int AGONES_SDK_TIMEOUT = readPortFromEnv("AGONES_SDK_TIMEOUT", 5000);

  private static int readPortFromEnv(String key, int defaultValue) {
    String value = System.getenv(key);
    if (value == null) {
      return defaultValue;
    }
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new NumberFormatException(
          "Cannot parse '%s' as port from environment variable '%s'".formatted(value, key));
    }
  }

  public static AgonesClient create(String address, int port) {
    return new AgonesClient(
        ManagedChannelBuilder.forAddress(address, port).usePlaintext().build(), AGONES_SDK_TIMEOUT);
  }

  public static AgonesClient create(String address) {
    return new AgonesClient(
        ManagedChannelBuilder.forAddress(address, AGONES_SDK_GRPC_PORT).usePlaintext().build(),
        AGONES_SDK_TIMEOUT);
  }

  public static AgonesClient create(int port) {
    return new AgonesClient(
        ManagedChannelBuilder.forAddress(AGONES_SDK_GRPC_HOST, port).usePlaintext().build(),
        AGONES_SDK_TIMEOUT);
  }

  public static AgonesClient create() {
    return new AgonesClient(
        ManagedChannelBuilder.forAddress(AGONES_SDK_GRPC_HOST, AGONES_SDK_GRPC_PORT)
            .usePlaintext()
            .build(),
        AGONES_SDK_TIMEOUT);
  }
}
