package dev.marfien.agones.observer;

import dev.agones.sdk.alpha.Count;
import io.grpc.stub.StreamObserver;

public interface LongStreamObserver {

    void onNext(long l);

    void onError(Throwable t);

    void onComplete();

    default StreamObserver<Count> toCountObserver() {
        return new StreamObserver<Count>() {
            @Override
            public void onNext(final Count value) {
                LongStreamObserver.this.onNext(value.getCount());
            }

            @Override
            public void onError(final Throwable t) {
                LongStreamObserver.this.onError(t);
            }

            @Override
            public void onCompleted() {
                LongStreamObserver.this.onComplete();
            }
        };
    }

}
