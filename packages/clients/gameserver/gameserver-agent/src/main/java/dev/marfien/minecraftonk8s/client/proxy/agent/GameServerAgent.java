package dev.marfien.minecraftonk8s.client.proxy.agent;

import dev.marfien.minecraftonk8s.client.common.ClientAgent;
import dev.marfien.minecraftonk8s.client.common.hook.PlayerDisconnectHook;
import dev.marfien.minecraftonk8s.client.proxy.agent.configuration.GameServerConfiguration;
import dev.marfien.minecraftonk8s.client.proxy.agent.internal.MinecraftServerInformer;
import net.kyori.adventure.text.Component;

public class GameServerAgent<I extends GameServerInterface, C extends GameServerConfiguration> extends
        ClientAgent<I, C> {

    public GameServerAgent(I clientInterface, C configuration) {
        super(clientInterface, configuration);
    }

}
