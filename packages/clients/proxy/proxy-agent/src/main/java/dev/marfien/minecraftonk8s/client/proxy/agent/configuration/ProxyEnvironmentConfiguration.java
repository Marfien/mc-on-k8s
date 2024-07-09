package dev.marfien.minecraftonk8s.client.proxy.agent.configuration;

import dev.marfien.minecraftonk8s.client.common.config.EnvironmentConfiguration;
import dev.marfien.minecraftonk8s.common.Environment;
import java.time.Duration;

public class ProxyEnvironmentConfiguration extends EnvironmentConfiguration implements ProxyConfiguration {

    private final Duration rebuildCacheInterval;
    private final Duration drainageDuration;
    private final Duration drainageDelay;

    private final String labelSeleector;

    public ProxyEnvironmentConfiguration() {
        super();
        this.rebuildCacheInterval = Duration.parse(Environment.get("REBUILD_CACHE_INTERVAL", "PT5M"));
        this.drainageDuration = Duration.parse(Environment.get("DRAINAGE_DURATION", "PT12H"));
        this.drainageDelay = Duration.parse(Environment.get("DRAINAGE_DELAY", "PT8H"));
        this.labelSeleector = Environment.get("LABEL_SELECTOR", "");
    }

    @Override
    public Duration getRebuildCacheInterval() {
        return this.rebuildCacheInterval;
    }

    @Override
    public Duration getDrainageDuration() {
        return this.drainageDuration;
    }

    @Override
    public Duration getDrainageDelay() {
        return this.drainageDelay;
    }

    @Override
    public String getLabelSelector() {
        return this.labelSeleector;
    }
}
