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

public class ResponseGUI {


    public static Inventory createGUI() {
        Inventory gui = Bukkit.createInventory(null, 54, "[RP]权限管理");
        MySQLManager mySQLManager = new MySQLManager();
        List<Map<String, String>> requestInfos = mySQLManager.getNotResolveRequest();
        int index = 0;
        for (Map<String, String> requestInfo : requestInfos) {
            String id = requestInfo.get("id");
            String name = requestInfo.get("name");
            String date = requestInfo.get("date");
            String requestId = requestInfo.get("requestId");
            String itemData = mySQLManager.getItemData(Integer.parseInt(requestId));
            if (itemData == null) {
                continue;
            }
            ItemStack item = Item.getItemStack(itemData);
            ItemMeta itemMeta = item.getItemMeta();
            List<String> info = new ArrayList<>();
            info.add("申请id: " + id);
            info.add("申请玩家: " + name);
            info.add("申请时间: " + date);
            info.add(ChatColor.GOLD + "左键同意申请，右键拒绝申请");
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
