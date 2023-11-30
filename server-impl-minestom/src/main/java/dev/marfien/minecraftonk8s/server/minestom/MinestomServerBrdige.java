package dev.marfien.minecraftonk8s.server.minestom;

import dev.marfien.minecraftonk8s.server.api.SubServerAgonesBridge;
import java.util.function.Consumer;
import net.minestom.server.event.player.PlayerLoginEvent;

public class MinestomServerBrdige extends SubServerAgonesBridge
    implements Consumer<PlayerLoginEvent> {

  @Override
  public void accept(PlayerLoginEvent playerLoginEvent) {
    super.handlePlayerLogin();
  }
}
