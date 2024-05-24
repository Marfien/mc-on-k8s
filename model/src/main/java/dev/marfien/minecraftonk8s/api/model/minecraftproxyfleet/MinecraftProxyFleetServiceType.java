package dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum MinecraftProxyFleetServiceType {

    @JsonAlias("LoadBalancer")
    LOAD_BALANCER,
    @JsonAlias("NodePort")
    NODE_PORT,
    @JsonAlias("ClusterIP")
    CLUSTER_IP,

}
