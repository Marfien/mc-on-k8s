package dev.marfien.minecraftonk8s.client.proxy.agent.internal;

import dev.marfien.minecraftonk8s.client.common.hook.PlayerConnectionHook;
import dev.marfien.minecraftonk8s.client.proxy.agent.ProxyAgent;
import dev.marfien.minecraftonk8s.client.proxy.agent.ProxyInterface;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DrainageHook implements PlayerConnectionHook {

    private final Logger logger = LoggerFactory.getLogger("DrainageHook");

    private final ProxyAgent<?, ?> agent;
    private final ProxyInterface proxyInterface;

    public DrainageHook(ProxyAgent<?, ?> agent, ProxyInterface proxyInterface) {
        this.agent = agent;
        this.proxyInterface = proxyInterface;
    }

    @Override
    public Component onPlayerConnect(UUID playerId) {
        if (this.agent.isDraining()) {
            this.logger.info("Player {} tried to connect while proxy is draining. Kicking...", playerId);
            return Component.text("Proxy is draining. No new players are accepted.");
        }

        return null;
    }

    @Override
    public void onPlayerDisconnect(UUID playerId) {
        this.proxyInterface.scheduleTask(() -> {
            if (!this.agent.isDraining())
                return;
            if (this.proxyInterface.getPlayerCount() == 0) {
                this.logger.info("Proxy is empty. Shutting down...");
                this.agent.shutdown();
            }
        }, 1);
    }
}
