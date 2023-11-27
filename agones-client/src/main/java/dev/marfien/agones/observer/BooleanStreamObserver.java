package dev.marfien.agones.observer;

import dev.agones.sdk.alpha.Bool;
import io.grpc.stub.StreamObserver;

public interface BooleanStreamObserver {

    void onNext(boolean b);

    void onError(Throwable t);

    void onComplete();

    default StreamObserver<Bool> toAlphaObserver() {
        return new StreamObserver<>() {
            @Override
            public void onNext(final Bool value) {
                BooleanStreamObserver.this.onNext(value.getBool());
            }

            @Override
            public void onError(final Throwable t) {
                BooleanStreamObserver.this.onError(t);
            }

            @Override
            public void onCompleted() {
                BooleanStreamObserver.this.onComplete();
            }
        };
    }

}
