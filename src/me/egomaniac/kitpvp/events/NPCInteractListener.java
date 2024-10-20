package me.egomaniac.kitpvp.events;

import me.egomaniac.kitpvp.ui.HowToPlayUI;
import me.egomaniac.kitpvp.ui.TagUI;
import me.egomaniac.kitpvp.ui.PerksUI;
import me.egomaniac.kitpvp.ui.ShopUI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCInteractListener implements Listener {

    private static ShopUI shopUI = new ShopUI();
    private static PerksUI perksUI = new PerksUI();

    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent event) {

        NPC npc = event.getNPC();
        Player player = event.getClicker();

        if (npc.getEntity().hasMetadata("merchant")) {
            shopUI.open(player);
        } else if (npc.getEntity().hasMetadata("perks")) {
            perksUI.open(player);
        } else if (npc.getEntity().hasMetadata("chattags")) {
            TagUI tags = new TagUI();
            tags.open(player);
        } else if (npc.getEntity().hasMetadata("basics")) {
            HowToPlayUI help = new HowToPlayUI();
            help.open(player);
        }

    }
}