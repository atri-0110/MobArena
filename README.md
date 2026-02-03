# MobArena

A PvE (Player vs Environment) arena system for AllayMC servers where players fight waves of mobs and earn rewards.

## Features

- **Wave-Based Combat**: Players fight through increasingly difficult waves of mobs
- **Multiple Arenas**: Create different arenas with unique settings
- **Reward System**: Earn points and rewards for surviving waves and defeating mobs
- **Player Stats**: Track kills, waves completed, and scores
- **Live Progress**: Real-time updates on wave progress and arena status
- **Automatic Wave Management**: Waves automatically progress at configurable intervals
- **Arena Listing**: View all available arenas and their status

## Commands

| Command | Description |
|---------|-------------|
| `/mobarena` | Show help message |
| `/mobarena join [arena]` | Join an arena (defaults to "Default") |
| `/mobarena leave` | Leave the current arena |
| `/mobarena list` | List all available arenas with status |
| `/mobarena stats` | Show your arena statistics (kills, wave, score) |

## Arena Mechanics

### Wave System
- Each arena has a configurable number of waves
- Waves automatically progress at set intervals
- Difficulty increases with each wave
- Complete all waves to earn bonus rewards

### Scoring
- **Kills**: +10 points per mob killed
- **Wave Completion**: +50 points per wave
- **Arena Completion**: +1000 bonus points

### Player Stats
- Total kills in the arena
- Current wave number
- Total score
- Number of arenas completed

## Configuration

Default arena settings:
- Max Players: 10
- Max Waves: 5
- Wave Interval: 100 seconds (1 minute 40 seconds)

## Usage Example

```
/mobarena list              # See available arenas
/mobarena join Default      # Join the default arena
/mobarena stats             # Check your progress
/mobarena leave             # Leave the arena
```

## Technical Details

- **API Version**: AllayMC 0.24.0
- **Java Version**: 21
- **Thread-Safe**: Uses ConcurrentHashMap for all player data
- **Event-Driven**: Listens to player join/quit events for arena management

## Future Enhancements

Potential features for future versions:
- Multiple mob types per wave
- Boss waves with special abilities
- Arena-specific loot drops
- Spectator mode
- Party/team support
- Custom arena configuration files
- Leaderboards across multiple sessions

## License

This plugin is provided as-is for use with AllayMC servers.

## Author

OpenClaw - [GitHub](https://github.com/daoge-cmd)
