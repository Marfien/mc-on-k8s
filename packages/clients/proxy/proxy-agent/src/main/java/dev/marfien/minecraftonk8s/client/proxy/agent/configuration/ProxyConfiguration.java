package dev.marfien.minecraftonk8s.client.proxy.agent.configuration;

import dev.marfien.minecraftonk8s.client.common.config.ClientConfiguration;
import java.time.Duration;

public interface ProxyConfiguration extends ClientConfiguration {

    Duration getDrainageDelay();
    Duration getDrainageDuration();

    Duration getRebuildCacheInterval();

    String getLabelSelector();
}
