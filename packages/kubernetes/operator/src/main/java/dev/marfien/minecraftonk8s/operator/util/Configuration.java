package dev.marfien.minecraftonk8s.operator.util;

import dev.marfien.minecraftonk8s.common.Environment;

public class Configuration {

    public static String MINECRAFT_SERVER_ALLOCATION_DEFAULTSTRATEGY = Environment.require("MINECRAFT_SERVER_ALLOCATION_DEFAULTSTRATEGY");

    public static String PROXY_FLEET_ALLOCATION_DEFAULTSTRATEGY = Environment.require("MINECRAFT_SERVER_ALLOCATION_DEFAULTSTRATEGY");

    public static String PROXY_SERVICE_ACCOUNT = Environment.require("PROXY_SERVICE_ACCOUNT");

}
