package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TrollCommand {
    public TrollCommand() {
        Main.getInstance().framework.registerCommands(this);
    }

    // stolen from crystal prac
    @Command(name = "troll", aliases = {"demo"}, permission = "kitpvp.admin", inGameOnly = true)
    public void execute(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if(args.length == 0) {
            player.sendMessage(CC.translate("&cUsage: /troll <player>"));
        }
        if (args.length > 0) {
            OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
            if (p.isOnline()) {
                Player enviar = Bukkit.getPlayer(args[0]);
                String path = Bukkit.getServer().getClass().getPackage().getName();
                String version = path.substring(path.lastIndexOf(".") + 1);
                try {
                    Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
                    Class<?> PacketPlayOutGameStateChange = Class.forName("net.minecraft.server." + version + ".PacketPlayOutGameStateChange");
                    Class<?> Packet = Class.forName("net.minecraft.server." + version + ".Packet");
                    Constructor<?> playOutConstructor = PacketPlayOutGameStateChange.getConstructor(Integer.TYPE, Float.TYPE);
                    Object packet = playOutConstructor.newInstance(5, 0);
                    Object craftPlayerObject = craftPlayer.cast(enviar);
                    Method getHandleMethod = craftPlayer.getMethod("getHandle");
                    Object handle = getHandleMethod.invoke(craftPlayerObject);
                    Object pc = handle.getClass().getField("playerConnection").get(handle);
                    Method sendPacketMethod = pc.getClass().getMethod("sendPacket", Packet);
                    sendPacketMethod.invoke(pc, packet);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                player.sendMessage(CC.translate("&a" + enviar.getName() + " &fgot trolled!"));
            }
        }
    }
}