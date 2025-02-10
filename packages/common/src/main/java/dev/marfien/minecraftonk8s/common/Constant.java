package dev.marfien.minecraftonk8s.common;

public class Constant {

    public static final String OPERATOR_NAME = "minecraftonk8s-operator";

    public static final String
            CRDS_GROUP = "mconk8s.marfien.dev",
            CRDS_VERSION = "v1alpha1";

    public static class Env {

        public static final String
                CONFIG_ALLOCATION_STRATEGY = "ALLOCATION_STRATEGY";

        public static final String
                PROXY_CONFIG_REBUILD_CACHE_INTERVALL = "PROXY_REBUILD_CACHE_INTERVAL",
                PROXY_CONFIG_DRAINAGE_TIMEOUT = "PROXY_DRAINAGE_TIMEOUT",
                PROXY_CONFIG_DRAINAGE_DELAY = "PROXY_DRAINAGE_DELAY",
                PROXY_CONFIG_LABEL_SELECTOR = "PROXY_LABEL_SELECTOR";

    }

    public static class K8sLabel {

        private static final String
                PREFIX = "app.kubernetes.io/";

        public static final String
            MANAGED_BY = PREFIX + "managed-by",
            INSTANCE = PREFIX + "instance",
            COMPONENT = PREFIX + "component";

    }

    public static class AppLabel {

        private static final String
                PREFIX = "mconk8s.marfien.dev/";

        public static final String
                PROXY_STATE = PREFIX + "draining";

        public static final String
                RECONCILER = PREFIX + "reconciler",
                BELONGS_TO = PREFIX + "belongs-to";

    }

    public static class ProxyState {

        public static final String
                DRAINING = "draining",
                RUNNING = "running";

    }

    public static class MinecraftServer {

        public static final String
                SHORT_NAME = "mcs",
                PLURAL = "minecraftservers",
                RECONCILER = "minecraft-server";

    }

    public static class MinecraftServerFleet {

        public static final String
                SHORT_NAME = "mcsf",
                PLURAL = "minecraftserverfleets",
                RECONCILER = "minecraft-server-fleet";

    }

    public static class MinecraftProxyFleet {

        public static final String
                SHORT_NAME = "mcpf",
                PLURAL = "minecraftproxyfleets",
                RECONCILER = "minecraft-proxy-fleet";

    }

}
