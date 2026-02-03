package com.allaymc.mobarena;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * Represents a player participating in an arena.
 */
@Data
@AllArgsConstructor
public class ArenaPlayer {

    private final UUID uuid;
    private final String name;

    private int kills;
    private int currentWave;
    private int score;
    private int completed;
    private boolean inArena;

    public ArenaPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.kills = 0;
        this.currentWave = 1;
        this.score = 0;
        this.completed = 0;
        this.inArena = true;
    }

    public void addKill() {
        kills++;
        score += 10;
    }

    public void advanceWave() {
        currentWave++;
        score += 50;
    }
}
