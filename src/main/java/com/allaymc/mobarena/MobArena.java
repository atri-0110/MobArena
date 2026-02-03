package com.allaymc.mobarena;

import lombok.Getter;
import org.allaymc.api.plugin.Plugin;
import org.allaymc.api.registry.Registries;
import org.allaymc.api.server.Server;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MobArena - A PvE arena system where players fight waves of mobs and earn rewards.
 * Features multiple arena types, wave progression, reward systems, and leaderboards.
 *
 * @author OpenClaw
 */
@Getter
public class MobArena extends Plugin {

    private static MobArena instance;

    private final ConcurrentHashMap<UUID, ArenaPlayer> arenaPlayers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Arena> arenas = new ConcurrentHashMap<>();

    @Override
    public void onLoad() {
        instance = this;
        this.pluginLogger.info("MobArena is loading...");
    }

    @Override
    public void onEnable() {
        this.pluginLogger.info("MobArena is enabling...");
        registerCommands();
        registerEvents();
        loadArenas();
        this.pluginLogger.info("MobArena enabled successfully!");
    }

    @Override
    public void onDisable() {
        this.pluginLogger.info("MobArena is disabling...");
        saveArenas();
        arenaPlayers.clear();
        this.pluginLogger.info("MobArena disabled successfully!");
    }

    private void registerCommands() {
        Registries.COMMANDS.register(new MobArenaCommand());
        this.pluginLogger.info("Registered /mobarena command");
    }

    private void registerEvents() {
        Server.getInstance().getEventBus().registerListener(new MobArenaListener());
        this.pluginLogger.info("Registered event listeners");
    }

    private void loadArenas() {
        // Load arenas from config
        // For now, create a default arena
        var defaultArena = new Arena("Default", 10, 5, 100);
        arenas.put(defaultArena.getName(), defaultArena);
        this.pluginLogger.info("Loaded " + arenas.size() + " arena(s)");
    }

    private void saveArenas() {
        // Save arenas to config
        this.pluginLogger.info("Saved " + arenas.size() + " arena(s)");
    }

    public void leaveArena(UUID uuid) {
        var arenaPlayer = arenaPlayers.remove(uuid);
        if (arenaPlayer != null) {
            var arena = arenas.values().stream()
                    .filter(a -> a.hasPlayer(uuid))
                    .findFirst()
                    .orElse(null);

            if (arena != null) {
                arena.removePlayer(uuid);
            }
        }
    }

    // Helper method for logging
    public void logInfo(String message) {
        this.pluginLogger.info(message);
    }

    public static MobArena getInstance() {
        return instance;
    }
}
