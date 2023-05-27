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
        if (mySQLManager.insertItem(Item.getItemData(itemStack), Item.getItemId(itemStack), command)) {
            player.sendMessage(ChatColor.GREEN + Item.getItemId(itemStack) + "添加权限成功");
        } else {
            player.sendMessage(ChatColor.RED + Item.getItemId(itemStack) + "添加权限失败,请查看是否已存在相同物品");
        }
        mySQLManager.closeConn();
    }
}
