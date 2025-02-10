package dev.marfien.minecraftonk8s.client.proxy.agent.configuration;

import dev.marfien.minecraftonk8s.client.api.config.EnvironmentConfiguration;
import dev.marfien.minecraftonk8s.common.Constant.Env;
import dev.marfien.minecraftonk8s.common.Environment;

public class ProxyEnvironmentConfiguration extends EnvironmentConfiguration implements ProxyConfiguration {

    private final int rebuildCacheIntervalMinutes;
    private final int drainageTimeoutHours;
    private final int drainageDelayHours;

    private final String labelSeleector;

    public ProxyEnvironmentConfiguration() {
        super();
        // TODO config
        this.rebuildCacheIntervalMinutes = Environment.requireInt(Env.PROXY_CONFIG_REBUILD_CACHE_INTERVALL);
        this.drainageTimeoutHours = Environment.requireInt(Env.PROXY_CONFIG_DRAINAGE_TIMEOUT);
        this.drainageDelayHours = Environment.requireInt(Env.PROXY_CONFIG_DRAINAGE_DELAY);
        this.labelSeleector = Environment.require(Env.PROXY_CONFIG_LABEL_SELECTOR);
    }

    @Override
    public int getRebuildCacheIntervalMinutes() {
        return this.rebuildCacheIntervalMinutes;
    }

    @Override
    public int getDrainageTimeoutHours() {
        return this.drainageTimeoutHours;
    }

    @Override
    public int getDrainageDelayHours() {
        return this.drainageDelayHours;
    }

    @Override
    public String getLabelSelector() {
        return this.labelSeleector;
    }
}
