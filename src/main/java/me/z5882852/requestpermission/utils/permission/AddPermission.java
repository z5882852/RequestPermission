package me.z5882852.requestpermission.utils.permission;

import me.z5882852.requestpermission.database.MySQLManager;
import me.z5882852.requestpermission.utils.Item;
import me.z5882852.requestpermission.utils.input.InputPrompt;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AddPermission {
    public static void addItemPermission(Player player, ItemStack itemStack, String command) {
        MySQLManager mySQLManager = new MySQLManager();
        try {
            if (mySQLManager.insertItem(Item.getItemData(itemStack), Item.getItemId(itemStack), command)) {
                player.sendMessage(ChatColor.GREEN + "添加权限成功");
            } else {
                player.sendMessage(ChatColor.RED + "添加权限失败,请查看是否已存在相同物品");
            }
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + "添加权限失败,无法解析该物品NBT数据");
        }
        mySQLManager.closeConn();
    }
}
