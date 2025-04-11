package dev.marfien.minecraftonk8s.operator.application;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "minecraftonk8s.operator")
public interface MinecraftOnK8sConfig {

    BinaryConfigMap binaryConfigMap();

    Proxy proxy();

    interface Proxy {

        String serviceaccountName();

    }

    interface BinaryConfigMap {

        String name();
        String namespace();

        boolean forceOverwrite();

    }

}
