package me.z5882852.requestpermission.utils.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ResponseGUI {


    public static Inventory createGUI() {
        Inventory gui = Bukkit.createInventory(null, 9, "测试");
        ItemStack item = new ItemStack(Material.IRON_BLOCK);
        gui.setItem(4, item);
        return gui;
    }
    public static void openGUI(Player player, int page) {
        Inventory gui = createGUI();
        player.openInventory(gui);
    }
}
