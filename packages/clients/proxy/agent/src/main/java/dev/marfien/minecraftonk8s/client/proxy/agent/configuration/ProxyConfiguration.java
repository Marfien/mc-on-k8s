package dev.marfien.minecraftonk8s.client.proxy.agent.configuration;

import dev.marfien.minecraftonk8s.client.api.config.ClientConfiguration;
import java.time.Duration;

public interface ProxyConfiguration extends ClientConfiguration {

    int getDrainageDelayHours();
    int getDrainageTimeoutHours();

    int getRebuildCacheIntervalMinutes();

    String getLabelSelector();
}
