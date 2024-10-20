package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import me.egomaniac.kitpvp.utils.cuboid.CustomLocation;
import org.bukkit.entity.Player;

public class SetLocationCommand {

    public SetLocationCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "setlocation", permission = "kitpvp.admin", usage = "&cUsage: /setlocation <type> <location>")
    public void execute(CommandArgs cmd) {
        Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length < 2) {
            player.sendMessage(CC.translate(cmd.getCommand().getUsage()));
            return;
        }

        String type = args[0];
        String locationType = args[1];

        switch (type) {
            case "lobby":
                handleLobbyLocation(player, locationType);
                break;
            case "sumo":
                handleSumoLocation(player, locationType);
                break;
            case "afk":
                handleAfkLocation(player, locationType);
                break;
            default:
                player.sendMessage(CC.translate(cmd.getCommand().getUsage()));
                break;
        }
    }

    private void handleLobbyLocation(Player player, String locationType) {
        switch (locationType) {
            case "spawn":
                Main.getInstance().spawnManager.setSpawnLocation(CustomLocation.fromBukkitLocation(player.getLocation()));
                saveLocation(player, "SERVER.SPAWN.LOCATION");
                player.sendMessage(CC.translate("&aSuccessfully saved lobby spawn location."));
                break;
            case "max":
                Main.getInstance().spawnManager.setSafezoneMax(CustomLocation.fromBukkitLocation(player.getLocation()));
                saveLocation(player, "SERVER.SPAWN.SAFEZONE-MAX");
                player.sendMessage(CC.translate("&aSuccessfully saved lobby safezone max location."));
                break;
            case "min":
                Main.getInstance().spawnManager.setSafezoneMin(CustomLocation.fromBukkitLocation(player.getLocation()));
                saveLocation(player, "SERVER.SPAWN.SAFEZONE-MIN");
                player.sendMessage(CC.translate("&aSuccessfully saved lobby safezone min location."));
                break;
            default:
                player.sendMessage(CC.translate("&cInvalid lobby location type. Available types: spawn, max, min"));
                break;
        }
    }

    private void handleAfkLocation(Player player, String locationType) {
        switch (locationType) {
            case "max":
                Main.getInstance().spawnManager.setSafezoneMax(CustomLocation.fromBukkitLocation(player.getLocation()));
                saveLocation(player, "SERVER.AFK.SAFEZONE-MAX");
                player.sendMessage(CC.translate("&aSuccessfully saved AFK safezone max location."));
                break;
            case "min":
                Main.getInstance().spawnManager.setSafezoneMin(CustomLocation.fromBukkitLocation(player.getLocation()));
                saveLocation(player, "SERVER.AFK.SAFEZONE-MIN");
                player.sendMessage(CC.translate("&aSuccessfully saved AFK safezone min location."));
                break;
            default:
                player.sendMessage(CC.translate("&cInvalid lobby location type. Available types: max, min"));
                break;
        }
    }

    private void handleSumoLocation(Player player, String locationType) {
        switch (locationType) {
            case "pos1":
                Main.getInstance().sumoManager.setPos1(CustomLocation.fromBukkitLocation(player.getLocation()));
                saveLocation(player, "SERVER.SUMO.POS1");
                player.sendMessage(CC.translate("&aSuccessfully saved sumo position 1."));
                break;
            case "pos2":
                Main.getInstance().sumoManager.setPos2(CustomLocation.fromBukkitLocation(player.getLocation()));
                saveLocation(player, "SERVER.SUMO.POS2");
                player.sendMessage(CC.translate("&aSuccessfully saved sumo position 2."));
                break;
            case "waiting":
                Main.getInstance().sumoManager.setWaitingLocation(CustomLocation.fromBukkitLocation(player.getLocation()));
                saveLocation(player, "SERVER.SUMO.WAITING-LOCATION");
                player.sendMessage(CC.translate("&aSuccessfully saved sumo waiting location."));
                break;
            case "max":
                Main.getInstance().sumoManager.setSafezoneMax(CustomLocation.fromBukkitLocation(player.getLocation()));
                saveLocation(player, "SERVER.SUMO.SAFEZONE-MAX");
                player.sendMessage(CC.translate("&aSuccessfully saved sumo safezone max location."));
                break;
            case "min":
                Main.getInstance().sumoManager.setSafezoneMin(CustomLocation.fromBukkitLocation(player.getLocation()));
                saveLocation(player, "SERVER.SUMO.SAFEZONE-MIN");
                player.sendMessage(CC.translate("&aSuccessfully saved sumo safezone min location."));
                break;
            default:
                player.sendMessage(CC.translate("&cInvalid sumo location type. Available types: pos1, pos2, waiting, max, min"));
                break;
        }
    }

    private void saveLocation(Player player, String location) {
        Main.getInstance().getConfig().set(location, CustomLocation.locationToString(CustomLocation.fromBukkitLocation(player.getLocation())));
        Main.getInstance().saveConfig();
    }
}