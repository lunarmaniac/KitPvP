package me.egomaniac.kitpvp.commands.misc;

import lombok.AllArgsConstructor;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class HealCommand {

    private Main plugin;

    public HealCommand() {
        Main.getInstance().framework.registerCommands(this);
    }
    @Command(name = "heal", permission = "kitpvp.admin", inGameOnly = true)
    public void execute(CommandArgs args) {
        if (args.length() == 0) {
            Player player = args.getPlayer();

            player.setHealth(20);
            player.setFoodLevel(20);
            args.getSender().sendMessage(CC.GOLD + "You have been healed.");
            return;
        }
        Player target = Main.getInstance().getServer().getPlayer(args.getArgs(0));

        target.setHealth(20);
        target.setFoodLevel(20);
        target.sendMessage(CC.GOLD + "You have been healed.");
        args.getSender().sendMessage(CC.GOLD + "You have healed " + CC.RESET + target.getName() + CC.GOLD + ".");
    }
}