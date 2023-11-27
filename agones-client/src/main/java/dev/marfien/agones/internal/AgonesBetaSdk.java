package dev.marfien.agones.internal;

import dev.agones.sdk.beta.SDKGrpc;
import io.grpc.ManagedChannel;

public class AgonesBetaSdk {

    private final SDKGrpc.SDKStub stub;

    public AgonesBetaSdk(ManagedChannel channel) {
        this.stub = SDKGrpc.newStub(channel);
    }

    public SDKGrpc.SDKStub getStub() {
        return this.stub;
    }
}
