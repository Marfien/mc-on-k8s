package dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MinecraftProxyFleetServiceType {

    @JsonProperty("LoadBalancer")
    LOAD_BALANCER("LoadBalancer"),

    @JsonProperty("NodePort")
    NODE_PORT("NodePort"),

    @JsonProperty("ClusterIP")
    CLUSTER_IP("ClusterIP");

    private final String kubeType;

}
