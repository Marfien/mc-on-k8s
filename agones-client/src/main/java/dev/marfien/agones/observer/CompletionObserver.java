package dev.marfien.agones.observer;

import io.grpc.stub.StreamObserver;

@FunctionalInterface
public interface CompletionObserver<T> extends StreamObserver<T> {

    @Override
    default void onNext(T value) {}

    @Override
    default void onError(Throwable t) {}

}
