package dev.marfien.minecraftonk8s.client.proxy.agent;

import dev.marfien.minecraftonk8s.client.common.ClientAgent;
import dev.marfien.minecraftonk8s.client.proxy.agent.configuration.ProxyConfiguration;

public class ProxyAgent<I extends ProxyInterface, C extends ProxyConfiguration> extends ClientAgent<I, C> {

    private boolean isDraining = false;

    protected ProxyAgent(I clientInterface, C configuration) {
        super(clientInterface, configuration);
    }

    @Override
    public void onStartup() {

        super.onStartup();
    }
}
