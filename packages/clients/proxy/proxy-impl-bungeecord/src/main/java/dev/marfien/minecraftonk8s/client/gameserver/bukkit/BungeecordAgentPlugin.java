package dev.marfien.minecraftonk8s.client.gameserver.bukkit;

import dev.marfien.minecraftonk8s.client.proxy.agent.ProxyAgent;
import dev.marfien.minecraftonk8s.client.proxy.agent.configuration.ProxyEnvironmentConfiguration;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeecordAgentPlugin extends Plugin {

    private final ProxyAgent<BungeecordProxyInterface, ProxyEnvironmentConfiguration> agent
            = new ProxyAgent<>(new BungeecordProxyInterface(this), new ProxyEnvironmentConfiguration());

    @Override
    public void onEnable() {
        this.agent.onStartup();
    }

    @Override
    public void onDisable() {
        this.agent.onShutdown();
    }
}
