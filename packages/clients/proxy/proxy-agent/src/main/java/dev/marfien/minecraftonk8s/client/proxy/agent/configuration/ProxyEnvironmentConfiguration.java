package dev.marfien.minecraftonk8s.client.proxy.agent.configuration;

import dev.marfien.minecraftonk8s.client.common.config.EnvironmentConfiguration;
import java.time.Duration;

public class ProxyEnvironmentConfiguration extends EnvironmentConfiguration implements ProxyConfiguration {

    private final Duration rebuildCacheInterval;
    private final Duration drainageDuration;
    private final Duration drainageDelay;

    private final String watchingNamespace;

    public ProxyEnvironmentConfiguration() {
        super();
        this.rebuildCacheInterval = System.getenv("REBUILD_CACHE_INTERVAL") == null
                ? Duration.ofMinutes(5)
                : Duration.parse(System.getenv("REBUILD_CACHE_INTERVAL"));
        this.drainageDuration = System.getenv("DRAINAGE_DURATION") == null
                ? Duration.ofHours(12)
                : Duration.parse(System.getenv("DRAINAGE_DURATION"));
        this.drainageDelay = System.getenv("DRAINAGE_DELAY") == null
                ? Duration.ofHours(8)
                : Duration.parse(System.getenv("DRAINAGE_DELAY"));
        this.watchingNamespace = System.getenv("WATCHING_NAMESPACE");
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
    public String getWatchingNamespace() {
        return this.watchingNamespace;
    }
}
