package me.z5882852.requestpermission.utils.permission;

import me.z5882852.requestpermission.RequestPermission;
import me.z5882852.requestpermission.database.MySQLManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Response {
    public static void acceptRequest(Player player, ItemStack itemStack) {
        int requestId = getItemRequestId(itemStack);
        if (requestId == -1) {
            player.sendMessage(ChatColor.RED + "无法获取请求ID");
            return;
        }
        MySQLManager mySQLManager = new MySQLManager();
        mySQLManager.resolveRequest(requestId);
        String command = mySQLManager.getCommand(mySQLManager.getRequestId(requestId));
        mySQLManager.closeConn();
        player.closeInventory();
        RequestPermission.thisPlugin.getServer().dispatchCommand(RequestPermission.thisPlugin.getServer().getConsoleSender(), command);
        player.sendMessage(ChatColor.GREEN + "已同意该申请");
    }

    public static void rejectRequest(Player player, ItemStack itemStack) {
        int requestId = getItemRequestId(itemStack);
        if (requestId == -1) {
            player.sendMessage(ChatColor.RED + "无法获取请求ID");
            return;
        }
        MySQLManager mySQLManager = new MySQLManager();
        mySQLManager.deleteRequest(requestId);
        mySQLManager.closeConn();
        player.closeInventory();
        player.sendMessage(ChatColor.GREEN + "已拒绝该申请");
    }

    public static int getItemRequestId(ItemStack itemStack) {
        int requestId = -1;
        try {
            String str = itemStack.getItemMeta().getLore().get(0).split(": ")[1];
            requestId = Integer.parseInt(str);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            RequestPermission.thisPlugin.getLogger().severe("无法获取请求ID" + e);
        }
        return requestId;
    }
}