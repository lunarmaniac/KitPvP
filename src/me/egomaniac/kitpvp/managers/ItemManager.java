package me.egomaniac.kitpvp.managers;

import me.egomaniac.kitpvp.ui.api.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagString;

public class ItemManager {

    private final String commandKey = "itemCommand";

    public ItemStack createCustomItem(ItemBuilder itemBuilder, String command) {
        ItemStack item = itemBuilder.toItemStack();
        item = setItemCommand(item, command);
        return item;
    }

    public ItemStack setItemCommand(ItemStack item, String command) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();

        tag.setString(commandKey, command); // Correct the method to set the string command
        nmsItem.setTag(tag);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public String getItemCommand(ItemStack item) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if (nmsItem.hasTag()) {
            NBTTagCompound tag = nmsItem.getTag();
            if (tag != null && tag.hasKey(commandKey)) {
                return tag.getString(commandKey); // Retrieve the string correctly from the tag
            }
        }
        return null;
    }
}
