package me.egomaniac.kitpvp;

import me.egomaniac.kitpvp.perks.PlayerPerksManager;
import me.egomaniac.kitpvp.scoreboard.Scoreboard;
import me.egomaniac.kitpvp.scoreboard.api.Assemble;
import me.egomaniac.kitpvp.scoreboard.api.AssembleStyle;
import me.egomaniac.kitpvp.tablist.TablistProvider;
import me.egomaniac.kitpvp.tablist.impl.Tablist;
import me.egomaniac.kitpvp.tasks.BroadcasterTask;
import me.egomaniac.kitpvp.ui.api.InventoryHandler;
import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.utils.ClassRegistrationController;
import me.egomaniac.kitpvp.utils.ConfigUtil;
import me.egomaniac.kitpvp.utils.DiscordWebhook;
import me.egomaniac.kitpvp.managers.*;
import me.egomaniac.kitpvp.utils.commandFramework.CommandFramework;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.UUID;

public class Main extends JavaPlugin {

    public static ConfigUtil settings;

    public static Main instance;
    private static InventoryHandler inventoryHandler;

    public StaffManager staffManager;
    public SpawnManager spawnManager;
    public SumoManager sumoManager;
    public TeleportManager teleportManager;
    public CombatManager combatManager;
    public EnderpearlManager enderpearlManager;

    public ProfileManager profileManager;
    public EconomyManager economyManager;
    public PlayerManager playerManager;
    public PlayerPerksManager playerPerksManager;
    public ItemManager itemManager;

    public static InventoryHandler getInventoryHandler() {
        return Main.inventoryHandler;
    }

    public static Main getInstance() {
        return Main.instance;
    }

    public final CommandFramework framework = new CommandFramework(this);
    private final ClassRegistrationController crc = new ClassRegistrationController();

    public void onEnable() {
        Main.instance = this;

        for (World w : Bukkit.getWorlds()) {
            w.getEntities().stream().filter(e -> !(e instanceof Player)).forEach(Entity::remove);
            w.setTime(6000);
            w.setDifficulty(Difficulty.NORMAL);
            w.setGameRuleValue("doDaylightCycle", "false");
            w.setGameRuleValue("doMobSpawning", "false");
        }

        this.loadScoreboard();
        this.loadEngine();
        this.loadManagers();

        crc.loadCommands("me.egomaniac.kitpvp.commands");
        crc.loadListeners("me.egomaniac.kitpvp.events");

        int commandsAmount = crc.getCommandsInt("me.egomaniac.kitpvp.commands");
        int eventsAmount = crc.getListenersInt("me.egomaniac.kitpvp.events");
        int profilesAmount = Main.getInstance().profileManager.profiles.size();

        Bukkit.getConsoleSender().sendMessage(CC.translate("&7&m-------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("          &6KitPvP &fhas been &aenabled"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7&m-------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&6&lKitPvP &7| &f" + commandsAmount + " &6commands have been registered."));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&6&lKitPvP &7| &f" + eventsAmount + " &6listeners have been registered."));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&6&lKitPvP &7| &f" + profilesAmount + " &6player profiles have been loaded."));
        DiscordWebhook.send("Server", "The server is now online", Color.GREEN, DiscordWebhook.Type.SERVER);
    }

    public void loadEngine() {
        new BroadcasterTask().runTaskTimerAsynchronously(this, 0L, 7000L);
        new Tablist(new TablistProvider(), this, 20L);

        Main.inventoryHandler = new InventoryHandler().init();

        this.createConfig();
    }

    public void loadScoreboard() {
        final Assemble assemble = new Assemble(this, new Scoreboard());
        assemble.setTicks(2L);
        assemble.setAssembleStyle(AssembleStyle.MODERN);
    }

    public void loadManagers() {
        this.spawnManager = new SpawnManager();
        this.sumoManager = new SumoManager();
        this.combatManager = new CombatManager();
        this.enderpearlManager = new EnderpearlManager();
        this.profileManager = new ProfileManager();
        this.playerManager = new PlayerManager();
        this.economyManager = new EconomyManager();
        this.teleportManager = new TeleportManager();
        this.playerPerksManager = new PlayerPerksManager();
        this.staffManager = new StaffManager();
        this.itemManager = new ItemManager();
    }

    public void createConfig() {
        Main.settings = new ConfigUtil(this, "config.yml", null);
        settings.saveDefaultConfig();
    }


    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7&m-------------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("          &6KitPvP &fhas been &cdisabled"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7&m-------------------------------------------"));

        for (UUID playerUUID : Main.getInstance().staffManager.getStaffModePlayers()) {
            Player player = Bukkit.getPlayer(playerUUID);
            if (player != null) {
                Main.getInstance().staffManager.disableStaffMode(player);
            }
        }

        settings.saveConfig();
        DiscordWebhook.send("Server", "The server is now offline.", Color.RED, DiscordWebhook.Type.SERVER);
    }
}
