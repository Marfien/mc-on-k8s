package dev.marfien.agones.internal;

import dev.agones.sdk.*;
import dev.agones.sdk.allocation.AllocationRequest;
import dev.agones.sdk.allocation.AllocationResponse;
import dev.agones.sdk.allocation.AllocationServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

public final class AgonesSdk {

  private final ManagedChannel channel;

  private final SdkGrpc.SdkStub stub;

  private final AllocationServiceGrpc.AllocationServiceStub allocationStub;

  public AgonesSdk(ManagedChannel channel) {
    this.channel = channel;
    this.stub = SdkGrpc.newStub(this.channel);
    this.allocationStub = AllocationServiceGrpc.newStub(channel);
  }

  public void allocateSelf(StreamObserver<Empty> observer) {
    this.stub.allocate(Empty.getDefaultInstance(), observer);
  }

  public void allocate(AllocationRequest request, StreamObserver<AllocationResponse> observer) {
    this.allocationStub.allocate(request, observer);
  }

  public void getGameServer(StreamObserver<GameServer> observer) {
    this.stub.getGameServer(Empty.getDefaultInstance(), observer);
  }

  public StreamObserver<Empty> health(StreamObserver<Empty> observer) {
    return this.stub.health(observer);
  }

  public void ready(StreamObserver<Empty> observer) {
    this.stub.ready(Empty.getDefaultInstance(), observer);
  }

  public void reserve(Duration duration, StreamObserver<Empty> observer) {
    this.stub.reserve(duration, observer);
  }

  public void setAnnotation(KeyValue keyValue, StreamObserver<Empty> observer) {
    this.stub.setAnnotation(keyValue, observer);
  }

  public void setAnnotation(String key, String value, StreamObserver<Empty> observer) {
    this.setAnnotation(KeyValue.newBuilder().setKey(key).setValue(value).build(), observer);
  }

  public void setLabel(KeyValue keyValue, StreamObserver<Empty> observer) {
    this.stub.setLabel(keyValue, observer);
  }

  public void setLabel(String key, String value, StreamObserver<Empty> observer) {
    this.setLabel(KeyValue.newBuilder().setKey(key).setValue(value).build(), observer);
  }

  public void shutdown(StreamObserver<Empty> observer) {
    this.stub.shutdown(Empty.getDefaultInstance(), observer);
  }

  public void watchGameServer(StreamObserver<GameServer> observer) {
    this.stub.watchGameServer(Empty.getDefaultInstance(), observer);
  }

  public SdkGrpc.SdkStub getStub() {
    return this.stub;
  }
}
