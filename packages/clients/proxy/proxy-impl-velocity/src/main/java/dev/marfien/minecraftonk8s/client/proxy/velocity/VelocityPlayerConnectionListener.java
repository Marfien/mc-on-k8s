package dev.marfien.minecraftonk8s.client.proxy.velocity;

import com.velocitypowered.api.event.ResultedEvent.ComponentResult;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import dev.marfien.minecraftonk8s.client.common.hook.PlayerConnectionHook;
import net.kyori.adventure.text.Component;

public class VelocityPlayerConnectionListener {

    private final PlayerConnectionHook hook;

    public VelocityPlayerConnectionListener(PlayerConnectionHook hook) {
        this.hook = hook;
    }

    @Subscribe
    public void onPlayerConnect(LoginEvent event) {
        Component component = this.hook.onPlayerConnect(event.getPlayer().getUniqueId());
        if (component != null) {
            event.setResult(ComponentResult.denied(component));
        }
    }

    @Subscribe
    public void onPlayerConnect(PostLoginEvent event) {
        this.hook.onPlayerConnected(event.getPlayer().getUniqueId());
    }

}
