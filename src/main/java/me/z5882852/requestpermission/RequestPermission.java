package me.z5882852.requestpermission;

import me.z5882852.requestpermission.commands.PlayerCommandExecutor;
import me.z5882852.requestpermission.database.MySQLTest;
import me.z5882852.requestpermission.utils.gui.ClickGUI;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RequestPermission extends JavaPlugin {
    public static RequestPermission thisPlugin;


    @Override
    public void onEnable() {
        getLogger().info("插件正在初始化中...");
        saveDefaultConfig();
        thisPlugin = this;

        Bukkit.getPluginCommand("requestpermission").setExecutor(new PlayerCommandExecutor(this));
        Bukkit.getServer().getPluginManager().registerEvents(new ClickGUI(this), this);

        new MySQLTest(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void reloadCfg(){
        this.reloadConfig();

    }
}
