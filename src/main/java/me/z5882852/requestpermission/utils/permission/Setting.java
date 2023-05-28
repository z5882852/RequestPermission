package me.z5882852.requestpermission.utils.permission;

import me.z5882852.requestpermission.database.MySQLManager;
import me.z5882852.requestpermission.utils.Item;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Setting {
    public static void resetPermissionCommand(Player player, ItemStack itemStack, String newCommand) {
        MySQLManager mySQLManager = new MySQLManager();
        if (mySQLManager.setCommand(Item.getItemId(itemStack), newCommand)) {
            player.sendMessage(ChatColor.GREEN + "指令设置成功!");
        } else {
            player.sendMessage(ChatColor.RED + "指令设置失败，请查看控制台信息。");
        }
        mySQLManager.closeConn();
    }

    public static void deletePermission(Player player, ItemStack itemStack) {
        MySQLManager mySQLManager = new MySQLManager();
        if (mySQLManager.deleteItem(Item.getItemId(itemStack))) {
            player.sendMessage(ChatColor.GREEN + "删除成功!");
        } else {
            player.sendMessage(ChatColor.RED + "删除失败，请查看控制台信息。");
        }
        mySQLManager.closeConn();
        player.closeInventory();
    }
}
