package dev.marfien.minecraftonk8s.client.common.config;

import dev.marfien.minecraftonk8s.client.common.AllocationStrategy;

public class EnvironmentConfiguration implements ClientConfiguration {

    private final AllocationStrategy allocationStrategy;

    public EnvironmentConfiguration() {
        this.allocationStrategy = System.getenv("ALLOCATION_STRATEGY") == null
                ? AllocationStrategy.PLAYERS
                : AllocationStrategy.valueOf(System.getenv("ALLOCATION_STRATEGY"));
    }

    @Override
    public AllocationStrategy getAllocationStrategy() {
        return this.allocationStrategy;
    }

}
