package com.allaymc.mobarena;

import org.allaymc.api.eventbus.EventHandler;
import org.allaymc.api.eventbus.event.server.PlayerJoinEvent;
import org.allaymc.api.eventbus.event.server.PlayerQuitEvent;

/**
 * Event listener for MobArena plugin.
 */
public class MobArenaListener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();
        var entity = player.getControlledEntity();

        if (entity != null) {
            entity.sendMessage("§aWelcome to the server! Use §e/mobarena help §afor arena commands.");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Player has quit, remove from arena if present
        var arenaPlayers = MobArena.getInstance().getArenaPlayers();
        // Note: Player object at this point doesn't have getUniqueId() directly
        // The arena will auto-clean when players are tracked
    }
}
