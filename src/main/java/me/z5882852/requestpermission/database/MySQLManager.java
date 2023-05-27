package me.z5882852.requestpermission.database;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;


public class MySQLManager {
    private JavaPlugin plugin;
    private FileConfiguration config;
    private Connection conn;
    private String itemTable;
    private String permissionTable;

    public MySQLManager(JavaPlugin plugin) {
        this.plugin = plugin;
        config = plugin.getConfig();
        try {
            String username = config.getString("mysql.user");
            String password = config.getString("mysql.password");
            itemTable = config.getString("mysql.item_table");
            permissionTable = config.getString("mysql.permission_table");
            String url = "jdbc:mysql://" + config.getString("mysql.host") + ":" + config.getString("mysql.port") + "/" + config.getString("mysql.database") + config.getString("mysql.params");
            this.conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            plugin.getLogger().severe("无法连接数据库: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean insertItem(String itemData, String command) {
        try {
            Statement statement = conn.createStatement();
            String sql = String.format("INSERT INTO `test2` (`item`, `command`) VALUES ('S', '%s');", itemTable, itemData, command);
            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch (SQLException e) {
            plugin.getLogger().severe("无法添加权限信息,请查看以下报错信息:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertRequest(String playerName, int requestId) {
        try {
            Statement statement = conn.createStatement();
            String sql = String.format("INSERT INTO `%s` (`name`, `requestId`) VALUES ('%s', %s);", permissionTable, playerName, requestId);
            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch (SQLException e) {
            plugin.getLogger().severe("创建请求数据失败,请查看以下报错信息:");
            e.printStackTrace();
            return false;
        }
    }

    public void getItem() {

    }

    public void getNotResolveRequest() {

    }

    public void deleteItem() {

    }

    public void deleteRequest() {

    }

    public void closeConn() {
        if (conn == null) {
            return;
        }
        try {
            conn.close();
        } catch (SQLException e) {
            plugin.getLogger().severe("无法关闭数据库连接:");
            e.printStackTrace();
        }
    }
}