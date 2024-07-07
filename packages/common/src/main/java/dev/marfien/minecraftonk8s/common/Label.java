package dev.marfien.minecraftonk8s.common;

public enum Label {

    PROXY_STATE("proxy-state"),
    COMPONENT("component"),
    BELONGS_TO("belongs-to"),
    CONTROLLED_BY("controlled-by");

    public static final String PREFIX = "mconk8s.marfien.dev";

    private final String name;

    Label(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String is(String value) {
        return this.name + "==" + value;
    }

    public String isNot(String value) {
        return this.name + "!=" + value;
    }

}
