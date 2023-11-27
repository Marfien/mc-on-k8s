package dev.marfien.agones.observer;

import dev.agones.sdk.alpha.Counter;
import dev.agones.sdk.alpha.PlayerIDList;
import dev.marfien.agones.model.CounterStore;
import dev.marfien.agones.model.ListStore;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.function.Function;

public class StreamObserverUtil {

    public static StreamObserver<PlayerIDList> listToAlphaSdkIdList(StreamObserver<List<String>> observer) {
        return new TransformedStreamObserver<>(idList -> List.copyOf(idList.getListList()), observer);
    }

    public static StreamObserver<dev.agones.sdk.alpha.List> listStoreToAgonesList(StreamObserver<ListStore> observer) {
        return new TransformedStreamObserver<>(ListStore::new, observer);
    }

    public static StreamObserver<Counter> counterStoreToAgonesList(StreamObserver<CounterStore> observer) {
        return new TransformedStreamObserver<>(CounterStore::new, observer);
    }

    private static class TransformedStreamObserver<B, T> implements StreamObserver<T> {

        private final Function<T, B> transform;
        private final StreamObserver<B> base;

        public TransformedStreamObserver(Function<T, B> transform, StreamObserver<B> base) {
            this.transform = transform;
            this.base = base;
        }

        @Override
        public void onNext(T value) {
            base.onNext(this.transform.apply(value));
        }

        @Override
        public void onError(Throwable t) {
            this.base.onError(t);
        }

        @Override
        public void onCompleted() {
            this.base.onCompleted();
        }
    }

}
