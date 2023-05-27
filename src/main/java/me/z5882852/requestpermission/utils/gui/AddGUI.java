package me.z5882852.requestpermission.utils.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AddGUI {
    public static Inventory createGUI() {
        Inventory gui = Bukkit.createInventory(null, 9, "[RP]添加权限");
        ItemStack item = new ItemStack(Material.IRON_BLOCK);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "请点击背包中要添加权限的物品");
        item.setItemMeta(itemMeta);
        gui.setItem(4, item);
        return gui;
    }
    public static void openGUI(Player player) {
        Inventory gui = createGUI();
        player.openInventory(gui);
    }
}
