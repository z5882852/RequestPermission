package me.z5882852.requestpermission.utils.gui;

import me.z5882852.requestpermission.database.MySQLManager;
import me.z5882852.requestpermission.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SettingGUI {

    public static Inventory createGUI() {
        Inventory gui = Bukkit.createInventory(null, 54, "[RP]权限设置");
        MySQLManager mySQLManager = new MySQLManager();
        List<Map<String, String>> requestInfos = mySQLManager.getAllItemPermission();
        int index = 0;
        for (Map<String, String> requestInfo : requestInfos) {
            String id = requestInfo.get("id");
            String itemData = requestInfo.get("itemData");
            String itemId = requestInfo.get("itemId");
            String command = requestInfo.get("command");
            ItemStack item = Item.getItemStack(itemData);
            if (item == null) {
                continue;
            }
            ItemMeta itemMeta = item.getItemMeta();
            List<String> info = new ArrayList<>();
            info.add("权限id: " + id);
            info.add("物品标识: " + itemId);
            info.add("指令: " + command);
            info.add(ChatColor.GOLD + "左键修改指令，右键删除");
            itemMeta.setLore(info);
            item.setItemMeta(itemMeta);
            gui.setItem(index, item);
            index++;
        }
        mySQLManager.closeConn();
        return gui;
    }
    public static void openGUI(Player player) {
        Inventory gui = createGUI();
        player.openInventory(gui);
    }
}
