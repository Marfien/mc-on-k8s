package dev.marfien.agones.observer;

import io.grpc.stub.StreamObserver;

public interface VoidStreamObserver {

    void onNext();

    void onError(Throwable t);

    void onCompleted();

    default StreamObserver<dev.agones.sdk.Empty> toSdk() {
        return new StreamObserver<>() {
            @Override
            public void onNext(final dev.agones.sdk.Empty value) {
                VoidStreamObserver.this.onNext();
            }

            @Override
            public void onError(final Throwable t) {
                VoidStreamObserver.this.onError(t);
            }

            @Override
            public void onCompleted() {
                VoidStreamObserver.this.onCompleted();
            }
        };
    }

    default StreamObserver<dev.agones.sdk.alpha.Empty> toAlpha() {
        return new StreamObserver<>() {
            @Override
            public void onNext(final dev.agones.sdk.alpha.Empty value) {
                VoidStreamObserver.this.onNext();
            }

            @Override
            public void onError(final Throwable t) {
                VoidStreamObserver.this.onError(t);
            }

            @Override
            public void onCompleted() {
                VoidStreamObserver.this.onCompleted();
            }
        };
    }

    public static final VoidStreamObserver NO_OP = new VoidStreamObserver() {
        @Override
        public void onNext() {}

        @Override
        public void onError(Throwable t) {}

        @Override
        public void onCompleted() {}
    };

}
