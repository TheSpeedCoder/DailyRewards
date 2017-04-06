package net.frozendev.dailyrewards.utils;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {

    public static ItemStack customItem(String name, Material material, int colorId, List<String> lore, ChatColor loreColor) {
        ItemStack item = new ItemStack(material, 1 , (short)colorId);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(loreColor + "" + name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
	
}
