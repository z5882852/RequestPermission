package me.z5882852.requestpermission.utils.permission;

import me.z5882852.requestpermission.database.MySQLManager;
import me.z5882852.requestpermission.utils.Item;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Request {
    public static void RequestItem(Player player, ItemStack itemStack) {
        MySQLManager mySQLManager = new MySQLManager();
        if (mySQLManager.checkHasItem(Item.getItemId(itemStack))) {
            if (mySQLManager.insertRequest(player.getName(), mySQLManager.getPermissionId(Item.getItemId(itemStack)))) {
                player.sendMessage(ChatColor.GREEN + Item.getItemId(itemStack) + "权限申请成功，等待管理员审核...");
            } else {
                player.sendMessage(ChatColor.RED + Item.getItemId(itemStack) + "请不要重复申请!");
            }
        } else {
            player.sendMessage(ChatColor.RED + Item.getItemId(itemStack) + "该物品不需要申请或者不能申请");
        }
        mySQLManager.closeConn();
    }
}
