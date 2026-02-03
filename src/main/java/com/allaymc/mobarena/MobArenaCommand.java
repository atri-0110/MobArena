package com.allaymc.mobarena;

import org.allaymc.api.command.Command;
import org.allaymc.api.command.tree.CommandContext;
import org.allaymc.api.command.tree.CommandTree;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.permission.Tristate;
import org.allaymc.api.server.Server;

/**
 * Command handler for MobArena plugin.
 */
public class MobArenaCommand extends Command {

    public MobArenaCommand() {
        super("mobarena", "Main command for MobArena", "mobarena.use");
        aliases.add("ma");
    }

    @Override
    public void prepareCommandTree(CommandTree tree) {
        tree.getRoot()
                // /mobarena join [arena]
                .key("join")
                .str("arena").optional()
                .exec(context -> {
                    if (!(context.getSender() instanceof EntityPlayer player)) {
                        context.getSender().sendMessage("§cThis command can only be used by players!");
                        return context.fail();
                    }

                    if (player.hasPermission("mobarena.use") != Tristate.TRUE) {
                        player.sendMessage("§cYou don't have permission to use this command!");
                        return context.fail();
                    }

                    String arenaName = "Default"; // Default arena name
                    var arena = MobArena.getInstance().getArenas().get(arenaName);
                    if (arena == null) {
                        player.sendMessage("§cArena '" + arenaName + "' not found!");
                        return context.fail();
                    }

                    var uuid = player.getUniqueId();
                    if (MobArena.getInstance().getArenaPlayers().containsKey(uuid)) {
                        player.sendMessage("§cYou are already in an arena!");
                        return context.fail();
                    }

                    var arenaPlayer = new ArenaPlayer(uuid, String.valueOf(uuid.hashCode()));
                    MobArena.getInstance().getArenaPlayers().put(uuid, arenaPlayer);
                    arena.addPlayer(arenaPlayer);

                    player.sendMessage("§aYou joined arena: §e" + arenaName);
                    player.sendMessage("§aWave 1 starting in 5 seconds...");

                    // Start the arena if it's the first player
                    if (arena.getPlayerCount() == 1) {
                        arena.startWave();
                    }

                    return context.success();
                })
                .root()
                // /mobarena leave
                .key("leave")
                .exec(context -> {
                    if (!(context.getSender() instanceof EntityPlayer player)) {
                        context.getSender().sendMessage("§cThis command can only be used by players!");
                        return context.fail();
                    }

                    if (player.hasPermission("mobarena.use") != Tristate.TRUE) {
                        player.sendMessage("§cYou don't have permission to use this command!");
                        return context.fail();
                    }

                    var uuid = player.getUniqueId();
                    if (!MobArena.getInstance().getArenaPlayers().containsKey(uuid)) {
                        player.sendMessage("§cYou are not in an arena!");
                        return context.fail();
                    }

                    MobArena.getInstance().leaveArena(uuid);
                    player.sendMessage("§aYou left the arena");
                    return context.success();
                })
                .root()
                // /mobarena list
                .key("list")
                .exec(context -> {
                    if (!(context.getSender() instanceof EntityPlayer player)) {
                        context.getSender().sendMessage("§cThis command can only be used by players!");
                        return context.fail();
                    }

                    if (player.hasPermission("mobarena.use") != Tristate.TRUE) {
                        player.sendMessage("§cYou don't have permission to use this command!");
                        return context.fail();
                    }

                    player.sendMessage("§6=== Available Arenas ===");
                    for (var arena : MobArena.getInstance().getArenas().values()) {
                        String status = arena.isRunning() ? "§aRunning" : "§cIdle";
                        player.sendMessage("§e" + arena.getName() + " §7- Players: " + arena.getPlayerCount() + "/" + arena.getMaxPlayers() + " §7[" + status + "§7]");
                    }
                    return context.success();
                })
                .root()
                // /mobarena stats
                .key("stats")
                .exec(context -> {
                    if (!(context.getSender() instanceof EntityPlayer player)) {
                        context.getSender().sendMessage("§cThis command can only be used by players!");
                        return context.fail();
                    }

                    if (player.hasPermission("mobarena.use") != Tristate.TRUE) {
                        player.sendMessage("§cYou don't have permission to use this command!");
                        return context.fail();
                    }

                    var uuid = player.getUniqueId();
                    var arenaPlayer = MobArena.getInstance().getArenaPlayers().get(uuid);

                    if (arenaPlayer == null) {
                        player.sendMessage("§cYou are not in an arena!");
                        return context.fail();
                    }

                    player.sendMessage("§6=== Your Arena Stats ===");
                    player.sendMessage("§eKills: §f" + arenaPlayer.getKills());
                    player.sendMessage("§eWave: §f" + arenaPlayer.getCurrentWave());
                    player.sendMessage("§eScore: §f" + arenaPlayer.getScore());
                    return context.success();
                })
                .root()
                // /mobarena help
                .key("help")
                .exec(context -> {
                    if (!(context.getSender() instanceof EntityPlayer player)) {
                        context.getSender().sendMessage("§cThis command can only be used by players!");
                        return context.fail();
                    }

                    player.sendMessage("§6=== MobArena Commands ===");
                    player.sendMessage("§e/mobarena join [arena] §7- Join an arena");
                    player.sendMessage("§e/mobarena leave §7- Leave the current arena");
                    player.sendMessage("§e/mobarena list §7- List all arenas");
                    player.sendMessage("§e/mobarena stats §7- Show your arena stats");
                    player.sendMessage("§e/mobarena help §7- Show this help message");
                    return context.success();
                });
    }
}
