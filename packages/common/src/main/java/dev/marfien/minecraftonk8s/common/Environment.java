package dev.marfien.minecraftonk8s.common;

import java.util.Map;

public class Environment {

    private static final Map<String, String> env = System.getenv();

    public static String get(String key) {
        return env.get(key);
    }

    public static String get(String key, String defaultValue) {
        return env.getOrDefault(key, defaultValue);
    }

    public static String require(String key) {
        String value = env.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing required environment variable: " + key);
        }
        return value;
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(get(key, Boolean.toString(defaultValue)));
    }

    public static boolean requireBoolean(String key) {
        return Boolean.parseBoolean(require(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static int getInt(String key, int defaultValue) {
        return Integer.parseInt(get(key, Integer.toString(defaultValue)));
    }

    public static int requireInt(String key) {
        return Integer.parseInt(require(key));
    }

    public static double getDouble(String key) {
        return Double.parseDouble(get(key));
    }

    public static double getDouble(String key, double defaultValue) {
        return Double.parseDouble(get(key, Double.toString(defaultValue)));
    }

    public static double requireDouble(String key) {
        return Double.parseDouble(require(key));
    }

}
