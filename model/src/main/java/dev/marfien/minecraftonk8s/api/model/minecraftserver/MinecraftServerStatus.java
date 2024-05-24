package dev.marfien.minecraftonk8s.api.model.minecraftserver;

public class MinecraftServerStatus {

    private boolean ready;
    private String ip;
    private int port;

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
