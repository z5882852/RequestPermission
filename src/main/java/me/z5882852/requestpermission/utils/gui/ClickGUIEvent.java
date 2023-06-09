package me.z5882852.requestpermission.utils.gui;

import me.z5882852.requestpermission.RequestPermission;
import me.z5882852.requestpermission.utils.input.InputPrompt;
import me.z5882852.requestpermission.utils.json.Json;
import me.z5882852.requestpermission.utils.permission.AddPermission;
import me.z5882852.requestpermission.utils.permission.Request;
import me.z5882852.requestpermission.utils.permission.Response;
import me.z5882852.requestpermission.utils.permission.Setting;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ClickGUIEvent implements Listener {
    public ClickGUIEvent(JavaPlugin plugin) {

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
        inventoryNames.add("[RP]权限管理");
        inventoryNames.add("[RP]权限设置");
        inventoryNames.add("[RP]添加权限");
        if (clickedInventory == null || !inventoryNames.contains(clickedInventory.getName())) {
            return;
        }
        event.setCancelled(true); // 取消玩家的点击事件
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().toString().equals("IRON_BLOCK") || clickedItem.equals(event.getView().getCursor())) {
            return;
        }
        clickedItem.setAmount(1);
        if (clickedInventory.getName().equals("[RP]申请权限")) {
            Request.RequestItem(player, clickedItem);
        } else if (clickedInventory.getName().equals("[RP]权限管理")) {
            if (event.isLeftClick()) {
                Response.acceptRequest(player, clickedItem);
            } else if (event.isRightClick()) {
                Response.rejectRequest(player, clickedItem);
            }
        } else if (clickedInventory.getName().equals("[RP]权限设置")) {
            if (event.isLeftClick()) {
                // 左键 修改指令
                Bukkit.getScheduler().runTaskLater(RequestPermission.thisPlugin, () -> player.closeInventory(), 2);
                InputPrompt.startInputConversation(player, input -> {
                    if (input != null) {
                        Setting.resetPermissionCommand(player, clickedItem, input);
                    } else {
                        player.sendMessage(ChatColor.RED + "输入被取消或中断。");
                    }
                });
            } else if (event.isRightClick()) {
                // 右键 删除
                Setting.deletePermission(player, clickedItem);
            }
        } else if (clickedInventory.getName().equals("[RP]添加权限")) {
            player.sendMessage(ChatColor.GOLD + "你选择了物品：" + clickedItem.getType().toString());
            Bukkit.getScheduler().runTaskLater(RequestPermission.thisPlugin, () -> player.closeInventory(), 2);
            InputPrompt.startInputConversation(player, input -> {
                if (input != null) {
                    AddPermission.addItemPermission(player, clickedItem, input);
                } else {
                    player.sendMessage(ChatColor.RED + "输入被取消或中断。");
                }
            });
        }
    }
}
