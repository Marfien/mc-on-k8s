package dev.marfien.agones.kubernetes.api.model;

public class GameServerPort {

    private String name;

    private String portPolicy;

    private String container;

    private int containerPort;

    private int hostPort;

    private String protocol;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortPolicy() {
        return this.portPolicy;
    }

    public void setPortPolicy(String portPolicy) {
        this.portPolicy = portPolicy;
    }

    public String getContainer() {
        return this.container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public int getContainerPort() {
        return this.containerPort;
    }

    public void setContainerPort(int containerPort) {
        this.containerPort = containerPort;
    }

    public int getHostPort() {
        return this.hostPort;
    }

    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
