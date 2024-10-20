package me.egomaniac.kitpvp.commands;

import me.egomaniac.kitpvp.utils.CC;
import me.egomaniac.kitpvp.Main;
import me.egomaniac.kitpvp.utils.commandFramework.Command;
import me.egomaniac.kitpvp.utils.commandFramework.CommandArgs;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class SetNPCCommand {

    public SetNPCCommand() {
        Main.getInstance().framework.registerCommands(this);
    }
    final Plugin CitizensPlugin = Bukkit.getPluginManager().getPlugin("Citizens");

    private final static String[] HELP_MESSAGE = new String[]{
            CC.translate("&7&m-------------------------------------------"),
            CC.translate("&6&lKitPvP &7\u2503 &fCommand Information"),
            CC.translate("&7&m-------------------------------------------"),
            CC.translate("&6/npc spawn &7\u2503 &fSpawn an NPC"),
            CC.translate("&6/npc select &7\u2503 &fSelect an NPC you are looking at"),
            CC.translate("&6/npc delete &7\u2503 &fDelete the NPC you have selected"),
            CC.translate("&7&m-------------------------------------------")
    };


    @Command(name = "testnpc", permission = "kitpvp.admin")
    public void execute(CommandArgs cmd) {
        final Player player = cmd.getPlayer();


        if (CitizensPlugin == null || !CitizensPlugin.isEnabled()) {
            player.sendMessage(CC.translate("&cYou are required to enable Citizens to use these commands."));
        }

        player.sendMessage(HELP_MESSAGE);
    }

    @Command(name = "testnpc.spawn", usage = "&cUsage: /npc spawn <type>", permission = "kitpvp.admin")
    public void npcSpawn(CommandArgs cmd) {
        final Player player = cmd.getPlayer();

        if (CitizensPlugin == null || !CitizensPlugin.isEnabled()) {
            player.sendMessage(CC.translate("&cCitizens must be enabled to use this command."));
            return;
        }

        String types = "merchant, tags, howtoplay, perks";

        String[] args = cmd.getArgs();
        if (args.length == 0) {
            player.sendMessage(CC.translate("&cAvailable NPC types: " + types));
            return;
        }

        String type = args[0].toLowerCase();
        String playerName;
        String infoLine;
        String TextureValue;
        String Signature;

        // TODO: needa figure a way to add multiple lines
        switch (type) {
            case "merchant":
                // playerName = "&b&LMerchant &8&m-&r &7&oPurchase an advantage&r";
                playerName = "merchant"; //&b&lMerchant
                infoLine = "&7&oPurchase an advantage";
                TextureValue = "ewogICJ0aW1lc3RhbXAiIDogMTY3MzU0NjkzNzk1MiwKICAicHJvZmlsZUlkIiA6ICI5ZWEyMTQ0NGFiNjI0MWZkYjg5YjE2NDFhNDg2MGZiZiIsCiAgInByb2ZpbGVOYW1lIiA6ICI3QUJDSE9VTiIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9hM2FjZGQ3N2QzMTY5ZTNjM2I2ZDRmZTg1ZjJhZDYwMjVjYjgxM2FjZTk1ODc2MmVmN2VkNWFlMTQ5ODM5M2Q3IgogICAgfQogIH0KfQ==";
                Signature = "G1MvFJFe8nJ2grMUr8dYKCg2gcC7zWPRI6ysdupqjjowRu67G4QES/NSQmPTt3oKka8zltn5oSVl5F2TI7NsJwBzmvK/B5h+9bGkrQYPUU5cyRDBOVbxewVz110kOLpXeID4eToeMvdQa9culSzS1Trcn3X/OwCdulfpLihAks343YxddqNB+hr9Oa//HvFTH3mQb4Pq14BBVJ6fdbU9VO4fHXw8mfft5P6JnWKe84E8/3NC5LPmeU5WnPAfivOfUfbd+BcR07OmoNYHWkdB91wRPg/2qh6LLIIffZqD3F5OetzoWtSqh4sLCuM1OdkCA1CCL4ZM0GaaS82Pms+OdCCc7IbXm6jIXpSqiGR68D9lwPnytxhGqxdK9Pd58KdjeyWqTaVrsyLtBJeDtVQhlaKyQsM7ecnOLkHFuRcBv4x/WuVIllJw+XYI9331xC9ltLUn8ITmPkUXBJZMscUV4tNVW8de78zgZH1UO/y13TQsTQDQXr7BPhHkMmGk4TDPEYsFaXS1v2aQjHlKiqeN9Xw5sdPJrhU6X9qeD2EZ7txsdUjkQJLxrjDHmzlIhRuFcv/euxHccKpWL7Xl2EuTuyB3yEZYfIPcatmJOHbDZZMTvaCz0eALNS6iiBw2uBxBsxsJvgm6EKdgMt5P/94rxjtB1wGxWddLh8tcY5knSwQ=";
                break;
            case "tags":
                playerName = "chattags"; //&e&LChat Tags
                infoLine = "&7&oStand out in the chat!";
                TextureValue = "ewogICJ0aW1lc3RhbXAiIDogMTY4OTA1ODA3Nzk5NSwKICAicHJvZmlsZUlkIiA6ICIyMDZlMWZkYjI5Yzk0NGYxOTQ5OTg4NzAwNTQxMGQ2NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJoNHlsMzMiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTA1ZWQ4OTgyZjk0MGMwMjhkNzJiODhkMDYwNGU4ZDEwZjc1OTFhNTVjZjk3ODk3Njg2ZDY4OTFiZDY1MjhmZSIKICAgIH0KICB9Cn0=";
                Signature = "Nt0Z7RuVbFJQDQx8mPRrjdyzveq2thBrrVXqhwxd84ybGdJ6CDiFmXiW5qgQcM7fEjW6gTk08N3+6AMVe0j/xpSc0gmEhX7yWetV6D+UBCJk2ZP2xGQbi3RmH9NNLvirK0lpSe7PffqBgMKJopmq9lQUb/azjZR1olcYk61hYdRMM8EoaJVk+QR+kJfICPckOOVo+cA+Wa5Fqiy2m6OtPIbgK0iueAgabmuJlXi8jhcF9OxqkXptIeE2AcLslaZ8IcVeaQhKGXnI6sWSvmRHnzulosZo4jcNCiDffdgtZz06SzCg2nRssH+vLOAZ06FQRYanpq0b5tARrAsbjwjL5mqtlEp/1Ibvie75ecp/d/WvooiUg4wqfuHVQUu8ZWctjgvDGRypTJ97cTMparNAWNFluGH6uLrxovEOYOnVSAZlHrPGNduVCojtrRyqitG6rTvhWWUutH54Mif91SrDU0dseaxQfc2guJiloNkVNVzJFomWkjaNgBkaFEgs95op6jvTX9InRoVOVTzP27lzTQJsWPppCzR8qg22U31B2i9t/+S0/jYOSEqlTUMjBfaLCgZTTNu7ypULcUFXWKTwg1IYdSo3ppbN8xakWEO/wB/cVGaqv+POJACGBzadB6K+GFcOl77ILo7HH5MMjnmtadS4XtXZAvfLALUpzMBVed4=";
                break;
            case "howtoplay":
                playerName = "basics"; //&a&LThe Basics
                infoLine = "&7&oLearn the server basics";
                TextureValue = "eyJ0aW1lc3RhbXAiOjE1NzUyMjAwMDAwMDIsInByb2ZpbGVJZCI6IjZiNTYxZmYxMmMwMTQ5NzY4MjAwZDg2NzY3ZDQ1MWE3IiwicHJvZmlsZU5hbWUiOiJtNHAiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2IwODM3MGY0OTg3YmMxYzUwYTk5MjkxOGMwN2NkMjc3YmRjYzMwZDQ3ZTdhZTIxYmZiMTAyODg5MTg3MmJiOWQifX19";
                Signature = "MGYShg+lhFh0v5zTJDnlkET60s5zlRj+K75di728/PnjCnyPgcsqaHBYOMR7e0yJdkkfn6XknIWkUQN+7Lt6vS1XOO4omSU8VdRvsx/m1HTd9MF98ljQLApVMcFxGewcRftwL8WELYmC8xnUthCYZlWpE5lo7yGxwaBCcLQAegh/+YI4he3WU/Ufl7wBezpXpPwYsxIn6bJH0pQTJF6TyqDoK2GkcFc4f89YRcSn/qNvHVJ5H16emIkjTUCkX9fJCQ5hHz35Fm7JgwAtCscH+/yJs4+WarVFN8hjiUYE4QJLpULvkM/ZI3+JwhVvCpDmKjWSx9YlDKwIc99y2Ej0iPox/WKgWY9RW0h6y0rdd18kM2zYSg6NLRuIi5GeObPSd0yxfNLZLOJAwST3gWJO2KgzSuK+fv1mqjN9sPZRDW7Ciru9lkKWI68MvW4m7R5hNeTwDHodqQKVUK+vHZjt6LZqwlTs2jQ+TkWKZUni16hEkYATu4cxbXTEdBY4GdLfRMi62rszDtjg3zeEFAwOh/r5Dsuwf0IPSilGIGA/MwM11r8P33AUkGYt3JToUrq/AxtLuJ7zQeS99SIXvQo3ZYsCU/h6ttDivnwhWejDrh9OFB5NRJoe+iBn6BOdQtc6dfyxO9XBQLvzSu3lnHSUCZH/pI7Q6aY+oDUxuXcNlcY=";
                break;
            case "perks":
                playerName = "perks"; //&5&LPerks
                infoLine = "&7&oEquip to enhance your gameplay";
                TextureValue = "ewogICJ0aW1lc3RhbXAiIDogMTcwNDczODQ1OTEyNSwKICAicHJvZmlsZUlkIiA6ICI4ZDYwNGY0NWM0OWQ0YWE2Yjc0MjhiNTJlYzcyYjliNSIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdGFyRG9ubiIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9lOWY3ZWU5MzgxOTdiMjAxZDVlYWIxZTcwMWExMjZhOGRkMmVhZjI5MjA0NTdmZGYxMWQxMmNkNDdkMGYyN2E3IgogICAgfQogIH0KfQ";
                Signature = "Y9Vc5m+ZMlWU53rwNGzEpVmXSW9A1/P+268dxFWViPz1XdCa6T6IEnzHTk6TUKUEi5Vc08zuWTerhFD4/lUObKYEVeXlCLq9+LLc7p97p4Lz/7ibM+kG/IrWF/AzopfcIwU3DRCQhc3YplgbyDtTnB/RC1sOneqebMTNcQsZWN5xkmN9uD4HAEkmpWIlF/J628sFkmhE3p51HRdDNdQCoXTnV1paWWYt+F5Tii/leiDb6ni+thDamrwcKmH5cUWIg6OhY5BkqHwYgHTN0arAt8uCHtKdrJ/J/VCurztgJc2IhnFVSPzkU8mQUzMXAUs9BlGl50XOSt0ARBTM3jPQ6GqmSGc1PziHntuIHPI5/tit28o/sPPIg5QTlTlBFDor+USmq/Bus7qB0g33rthoYSPavPzVp0VzuWSoUX2OzQ/fzhyYKNNzdybYsOfw/x8UKXZp7fb3uj1kl+eF5UxNopH3Bx56Mtapz1/ECtpG3MiHtKvo2FatPEZs+ijz++bbqKW91ldbLM+wadeUogHSMN/a3d//rAOGGJryE10OCH9dzXV34K47Ca2gzZL6shvnyeeqZuCFwS8V+Pbwh2VS2WKxSFQr61BIKlVHxBx/hpuTOaGR6Zoc/A0FeU2nAJ10322UUDCk+daTar2nruQABh8tpXmRZv/Es/ybc45atWo=";
                break;
            default:
                player.sendMessage(CC.translate("&cInvalid NPC type. Available types: " + types));
                return;
        }

        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "");

        SkinTrait skinTrait = npc.getOrAddTrait(SkinTrait.class);
        skinTrait.clearTexture();

        skinTrait.setTexture(TextureValue, Signature);
        skinTrait.setSkinPersistent("custom", Signature, TextureValue);

        if (npc.isSpawned()) {
            npc.despawn();
        }

        npc.spawn(player.getLocation());
        npc.getEntity().setMetadata(playerName, new FixedMetadataValue(Main.getInstance(), true));

        player.sendMessage(CC.translate("&aYou have spawned the NPC: &e" + npc.getName()));
    }

    @Command(name = "testnpc.select", permission = "kitpvp.admin")
    public void selectNPC(CommandArgs cmd) {
        final Player player = cmd.getPlayer();

        if (CitizensPlugin == null || !CitizensPlugin.isEnabled()) {
            player.sendMessage(CC.translate("&cCitizens must be enabled to use this command."));
            return;
        }

        player.sendMessage(CC.translate("&eBe aware, This command will temporarily execute npc2 select. "));
        player.performCommand("npc2 select");

    }

    @Command(name = "testnpc.delete", permission = "kitpvp.admin")
    public void npcDelete(CommandArgs cmd) {
        final Player player = cmd.getPlayer();

        if (CitizensPlugin == null || !CitizensPlugin.isEnabled()) {
            player.sendMessage(CC.translate("&cCitizens must be enabled to use this command."));
            return;
        }


        NPC npc = CitizensAPI.getDefaultNPCSelector().getSelected(player);

        if (npc != null) {
            npc.destroy();
            player.sendMessage(CC.translate("&aYou have deleted the NPC: &e" + npc.getName()));
        } else {
            player.sendMessage(CC.translate("&cPlease select an NPC before deleting."));
        }
    }

}