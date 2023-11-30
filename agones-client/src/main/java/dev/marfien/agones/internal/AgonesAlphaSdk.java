package dev.marfien.agones.internal;

import com.google.protobuf.FieldMask;
import dev.agones.sdk.alpha.*;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

public class AgonesAlphaSdk {

  private final SDKGrpc.SDKStub stub;

  public AgonesAlphaSdk(ManagedChannel channel) {
    this.stub = SDKGrpc.newStub(channel);
  }

  public void addList(String name, String value, StreamObserver<List> observer) {
    this.stub.addListValue(
        AddListValueRequest.newBuilder().setName(name).setValue(value).build(), observer);
  }

  public void getConnectedPlayers(StreamObserver<PlayerIDList> observer) {
    this.stub.getConnectedPlayers(Empty.getDefaultInstance(), observer);
  }

  public void getCounter(String name, StreamObserver<Counter> observer) {
    this.stub.getCounter(GetCounterRequest.newBuilder().setName(name).build(), observer);
  }

  public void getList(String name, StreamObserver<List> observer) {
    this.stub.getList(GetListRequest.newBuilder().setName(name).build(), observer);
  }

  public void getPlayerCapacity(StreamObserver<Count> observer) {
    this.stub.getPlayerCapacity(Empty.getDefaultInstance(), observer);
  }

  public void getPlayerCount(StreamObserver<Count> observer) {
    this.stub.getPlayerCount(Empty.getDefaultInstance(), observer);
  }

  public void isPlayerConnected(PlayerID playerId, StreamObserver<Bool> observer) {
    this.stub.isPlayerConnected(playerId, observer);
  }

  public void isPlayerConnected(String playerId, StreamObserver<Bool> observer) {
    this.isPlayerConnected(PlayerID.newBuilder().setPlayerID(playerId).build(), observer);
  }

  public void playerConnect(PlayerID playerId, StreamObserver<Bool> observer) {
    this.stub.playerConnect(playerId, observer);
  }

  public void playerConnect(String playerId, StreamObserver<Bool> observer) {
    this.playerConnect(PlayerID.newBuilder().setPlayerID(playerId).build(), observer);
  }

  public void playerDisconnect(PlayerID playerId, StreamObserver<Bool> observer) {
    this.stub.playerDisconnect(playerId, observer);
  }

  public void playerDisconnect(String playerId, StreamObserver<Bool> observer) {
    this.playerDisconnect(PlayerID.newBuilder().setPlayerID(playerId).build(), observer);
  }

  public void removeList(String name, String value, StreamObserver<List> observer) {
    this.stub.removeListValue(
        RemoveListValueRequest.newBuilder().setName(name).setValue(value).build(), observer);
  }

  public void setPlayerCapacity(Count capacity, StreamObserver<Empty> observer) {
    this.stub.setPlayerCapacity(capacity, observer);
  }

  public void updateCounter(
      Counter counter, FieldMask updateMask, StreamObserver<Counter> observer) {
    this.stub.updateCounter(
        UpdateCounterRequest.newBuilder().setCounter(counter).setUpdateMask(updateMask).build(),
        observer);
  }

  public void updateList(List list, FieldMask updateMask, StreamObserver<List> observer) {
    this.stub.updateList(
        UpdateListRequest.newBuilder().setList(list).setUpdateMask(updateMask).build(), observer);
  }

  public SDKGrpc.SDKStub getStub() {
    return this.stub;
  }
}
