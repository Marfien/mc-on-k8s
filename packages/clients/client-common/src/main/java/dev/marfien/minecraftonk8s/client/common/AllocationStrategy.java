package dev.marfien.minecraftonk8s.client.common;

public enum AllocationStrategy {

    MANUAL,
    PLAYERS,
    ALWAYS;

    private static final AllocationStrategy CONFIGURED_STRATEGY;

    static {
        String strategy = System.getenv("ALLOCATION_STRATEGY");
        CONFIGURED_STRATEGY = AllocationStrategy.valueOf(strategy);
    }

}
