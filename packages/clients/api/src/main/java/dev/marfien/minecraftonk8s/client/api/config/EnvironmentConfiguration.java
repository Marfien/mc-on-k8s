package dev.marfien.minecraftonk8s.client.api.config;

import dev.marfien.minecraftonk8s.client.api.AllocationStrategy;
import dev.marfien.minecraftonk8s.common.Environment;

public class EnvironmentConfiguration implements ClientConfiguration {

    private final AllocationStrategy allocationStrategy;

    protected EnvironmentConfiguration() {
        this.allocationStrategy = AllocationStrategy.valueOf(Environment.get("ALLOCATION_STRATEGY", Environment.require("DEFAULT_ALLOCATION_STRATEGY")));
    }

    @Override
    public AllocationStrategy getAllocationStrategy() {
        return this.allocationStrategy;
    }

}
