package me.egomaniac.kitpvp.commands.misc;

import lombok.AllArgsConstructor;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class GamemodeCommand {

    private Main plugin;

    public GamemodeCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "gamemode.s", aliases = {"gms", "gm.s", "gm0", "gm.0", "gamemode.0"}, permission = "kitpvp.admin", inGameOnly = true)
    public void survival(CommandArgs args) {
        if (args.length() == 0) {
            Player player = args.getPlayer();

            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(CC.GREEN + "Your gamemode has been updated to " + CC.YELLOW + player.getGameMode().toString() + CC.GREEN + ".");
            return;
        }
        Player target = Main.getInstance().getServer().getPlayer(args.getArgs(0));
        if (target == null) {
            args.getSender().sendMessage(CC.RED + "Could not find player.");
            return;
        }
        target.setGameMode(GameMode.SURVIVAL);
        target.sendMessage(CC.GREEN + "Your gamemode has been updated to " + CC.YELLOW + target.getGameMode().toString() + CC.GREEN + ".");
        args.getSender().sendMessage(CC.GREEN + "You have updated " + CC.YELLOW + target.getName() + "'s " + CC.GREEN + "gamemode to " + CC.YELLOW + target.getGameMode() + CC.GREEN + ".");
    }

    @Command(name = "gamemode.c", aliases = {"gmc", "gm.c", "gm1", "gm.1", "gamemode.1"}, permission = "kitpvp.admin", inGameOnly = true)
    public void creative(CommandArgs args) {
        if (args.length() == 0) {
            Player player = args.getPlayer();

            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(CC.GREEN + "Your gamemode has been updated to " + CC.YELLOW + player.getGameMode().toString() + CC.GREEN + ".");
            return;
        }
        Player target = Main.getInstance().getServer().getPlayer(args.getArgs(0));
        if (target == null) {
            args.getSender().sendMessage(CC.RED + "Could not find player.");
            return;
        }
        target.setGameMode(GameMode.CREATIVE);
        target.sendMessage(CC.GREEN + "Your gamemode has been updated to " + CC.YELLOW + target.getGameMode().toString() + CC.GREEN + ".");
        args.getSender().sendMessage(CC.GREEN + "You have updated " + CC.YELLOW + target.getName() + "'s " + CC.GREEN + "gamemode to " + CC.YELLOW + target.getGameMode() + CC.GREEN + ".");
    }

    @Command(name = "gamemode.a", aliases = {"gma", "gm.a", "gm2", "gm.2", "gamemode.2"}, permission = "kitpvp.admin", inGameOnly = true)
    public void adventure(CommandArgs args) {
        if (args.length() == 0) {
            Player player = args.getPlayer();

            player.setGameMode(GameMode.ADVENTURE);
            player.sendMessage(CC.GREEN + "Your gamemode has been updated to " + CC.YELLOW + player.getGameMode().toString() + CC.GREEN + ".");
            return;
        }
        Player target = Main.getInstance().getServer().getPlayer(args.getArgs(0));
        if (target == null) {
            args.getSender().sendMessage(CC.RED + "Could not find player.");
            return;
        }
        target.setGameMode(GameMode.ADVENTURE);
        target.sendMessage(CC.GREEN + "Your gamemode has been updated to " + CC.YELLOW + target.getGameMode().toString() + CC.GREEN + ".");
        args.getSender().sendMessage(CC.GREEN + "You have updated " + CC.YELLOW + target.getName() + "'s " + CC.GREEN + "gamemode to " + CC.YELLOW + target.getGameMode() + CC.GREEN + ".");
    }

    @Command(name = "gamemode.spectator", aliases = {"gmsp", "gm.sp", "gm3", "gm.3", "gamemode.3"}, permission = "kitpvp.admin", inGameOnly = true)
    public void spectator(CommandArgs args) {
        if (args.length() == 0) {
            Player player = args.getPlayer();

            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(CC.GREEN + "Your gamemode has been updated to " + CC.YELLOW + player.getGameMode().toString() + CC.GREEN + ".");
            return;
        }
        Player target = Main.getInstance().getServer().getPlayer(args.getArgs(0));
        if (target == null) {
            args.getSender().sendMessage(CC.RED + "Could not find player.");
            return;
        }
        target.setGameMode(GameMode.SPECTATOR);
        target.sendMessage(CC.GREEN + "Your gamemode has been updated to " + CC.YELLOW + target.getGameMode().toString() + CC.GREEN + ".");
        args.getSender().sendMessage(CC.GREEN + "You have updated " + CC.YELLOW + target.getName() + "'s " + CC.GREEN + "gamemode to " + CC.YELLOW + target.getGameMode() + CC.GREEN + ".");
    }

}
