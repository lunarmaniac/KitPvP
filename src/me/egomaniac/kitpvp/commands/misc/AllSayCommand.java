package me.egomaniac.kitpvp.commands.misc;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AllSayCommand
{
    public AllSayCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    @Command(name = "allsay", permission = "kitpvp.player", usage = "&cUsage: /allsay <message>", inGameOnly = true)
    public void execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();
        String[] args = cmd.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&c" + cmd.getCommand().getUsage()));
            return;
        }

        StringBuilder message = new StringBuilder();
        for (int i = 0; i < args.length; i++){
            message.append(args[i]).append(" ");
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.chat(String.valueOf(message));
        }
    }
}
