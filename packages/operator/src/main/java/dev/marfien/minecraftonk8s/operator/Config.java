package dev.marfien.minecraftonk8s.operator;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "minecraftonk8s.operator")
public interface Config {

    Proxy proxy();

    interface Proxy {

        String serviceaccountName();

    }

}
