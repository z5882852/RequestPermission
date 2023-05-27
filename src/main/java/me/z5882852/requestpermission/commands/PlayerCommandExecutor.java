package me.z5882852.requestpermission.commands;

import me.z5882852.requestpermission.utils.gui.RequestGUI;
import me.z5882852.requestpermission.RequestPermission;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class PlayerCommandExecutor implements CommandExecutor {
    private JavaPlugin plugin;

    public PlayerCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "用法:/rp <open/add/setting/manage/reload>");
            return true;
        }
        if (args[0].equalsIgnoreCase("open")) {
            if (sender.hasPermission("requestpermission.default.open")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "[WorldLimits]您不是一个玩家。");
                    return false;
                }
                Player player = (Player) sender;
                RequestGUI.openGUI(player);
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "你没有执行该命令的权限。");
            }
        } else if (args[0].equalsIgnoreCase("add")) {
            if (sender.hasPermission("requestpermission.admin.add")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "[WorldLimits]您不是一个玩家。");
                    return false;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "你没有执行该命令的权限。");
            }
        } else if (args[0].equalsIgnoreCase("setting")) {
            if (sender.hasPermission("requestpermission.admin.setting")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "[WorldLimits]您不是一个玩家。");
                    return false;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "你没有执行该命令的权限。");
            }
        } else if (args[0].equalsIgnoreCase("manage")) {
            if (sender.hasPermission("requestpermission.admin.manage")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "[WorldLimits]您不是一个玩家。");
                    return false;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "你没有执行该命令的权限。");
            }
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("requestpermission.admin.reload")) {
                RequestPermission.thisPlugin.reloadCfg();

            } else {
                sender.sendMessage(ChatColor.RED + "你没有执行该命令的权限。");
            }
        }
        return false;
    }
}