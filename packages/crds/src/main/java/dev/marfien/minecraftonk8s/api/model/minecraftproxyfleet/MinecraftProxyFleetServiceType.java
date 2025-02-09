package dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MinecraftProxyFleetServiceType {

    @JsonProperty("LoadBalancer")
    LOAD_BALANCER,

    @JsonProperty("NodePort")
    NODE_PORT,

    @JsonProperty("ClusterIP")
    CLUSTER_IP,

}
