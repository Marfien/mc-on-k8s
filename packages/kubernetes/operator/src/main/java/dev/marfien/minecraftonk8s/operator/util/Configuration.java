package dev.marfien.minecraftonk8s.operator.util;

public class Configuration {

    public static String MINECRAFT_SERVER_ALLOCATION_DEFAULTSTRATEGY = System.getenv("MINECRAFT_SERVER_ALLOCATION_DEFAULTSTRATEGY");

    public static String PROXY_FLEET_ALLOCATION_DEFAULTSTRATEGY = System.getenv("MINECRAFT_SERVER_ALLOCATION_DEFAULTSTRATEGY");

    public static String PROXY_SERVICE_ACCOUNT = System.getenv("PROXY_SERVICE_ACCOUNT");

}
