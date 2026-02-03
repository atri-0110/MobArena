package com.allaymc.mobarena;

import lombok.Getter;
import lombok.Setter;
import org.allaymc.api.server.Server;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents an arena where players fight waves of mobs.
 */
@Getter
@Setter
public class Arena {

    private final String name;
    private final int maxPlayers;
    private final int maxWaves;
    private final int waveInterval;

    private int currentWave;
    private boolean running;
    private final Set<UUID> players;
    private long lastWaveTime;

    public Arena(String name, int maxPlayers, int maxWaves, int waveInterval) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.maxWaves = maxWaves;
        this.waveInterval = waveInterval;
        this.currentWave = 0;
        this.running = false;
        this.players = ConcurrentHashMap.newKeySet();
    }

    public void addPlayer(ArenaPlayer arenaPlayer) {
        players.add(arenaPlayer.getUuid());
    }

    public void removePlayer(UUID uuid) {
        players.remove(uuid);

        // Stop arena if no players left
        if (players.isEmpty()) {
            stop();
        }
    }

    public boolean hasPlayer(UUID uuid) {
        return players.contains(uuid);
    }

    public int getPlayerCount() {
        return players.size();
    }

    public void startWave() {
        currentWave++;
        running = true;
        lastWaveTime = System.currentTimeMillis();

        if (currentWave > maxWaves) {
            completeArena();
            return;
        }

        MobArena.getInstance().logInfo("Wave " + currentWave + " started in arena: " + name);

        // Schedule next wave
        Server.getInstance().getScheduler().scheduleDelayed(
                MobArena.getInstance(),
                this::startWave,
                waveInterval * 20 // Convert seconds to ticks
        );
    }

    public void stop() {
        running = false;
        currentWave = 0;
        players.clear();
        MobArena.getInstance().logInfo("Arena '" + name + "' stopped");
    }

    public void completeArena() {
        running = false;
        MobArena.getInstance().logInfo("Arena '" + name + "' completed! All " + maxWaves + " waves survived.");

        // Award rewards to all players
        players.forEach(uuid -> {
            var arenaPlayer = MobArena.getInstance().getArenaPlayers().get(uuid);
            if (arenaPlayer != null) {
                arenaPlayer.setScore(arenaPlayer.getScore() + 1000);
                arenaPlayer.setCompleted(arenaPlayer.getCompleted() + 1);
            }
        });

        // Schedule arena reset
        Server.getInstance().getScheduler().scheduleDelayed(
                MobArena.getInstance(),
                () -> {
                    currentWave = 0;
                    players.clear();
                },
                5 * 20
        );
    }
}
