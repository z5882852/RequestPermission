package me.z5882852.requestpermission.utils.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ClickGUI implements Listener {
    public ClickGUI(JavaPlugin plugin) {

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // 检查事件发生的玩家是否是玩家自身
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();

        Inventory clickedInventory = event.getInventory();
        List<String> inventoryNames = new ArrayList<>();
        inventoryNames.add("[RP]申请权限");
        inventoryNames.add("[RP]管理权限");
        inventoryNames.add("[RP]权限设置");
        if (clickedInventory == null || !inventoryNames.contains(clickedInventory.getName())) {
            return;
        }
        event.setCancelled(true); // 取消玩家的点击事件
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().toString().equals("IRON_BLOCK") || clickedItem.equals(event.getView().getCursor())) {
            return;
        }
        player.sendMessage("你点击了物品：" + clickedItem.getType().toString());
        if (clickedInventory.getName().equals("[RP]申请权限")) {
            String itemData = clickedItem.serialize().toString();
            System.out.println(itemData);
        } else if (clickedInventory.getName().equals("[RP]管理权限")) {

        } else if (clickedInventory.getName().equals("[RP]权限设置")) {

        }
    }
}
