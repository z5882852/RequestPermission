package me.z5882852.requestpermission.database;

import me.z5882852.requestpermission.RequestPermission;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MySQLManager {
    private JavaPlugin plugin;
    private FileConfiguration config;
    private Connection conn;
    private String itemTable;
    private String permissionTable;

    public MySQLManager() {
        this.plugin = RequestPermission.thisPlugin;
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

    public boolean insertItem(String itemData,String itemId, String command) {
        if (getPermissionId(itemId) != -1) {
            return false;
        }
        try {
            Statement statement = conn.createStatement();
            String sql = String.format("INSERT INTO `%s` (`item`, `itemId`, `command`) VALUES ('%s', '%s', '%s');", itemTable, itemData, itemId, command);
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
        if (getRequestIds(playerName).contains(requestId)) {
            return false;
        }
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

    public int getPermissionId(String itemId) {
        int permissionId = -1;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT `id` FROM `%s` WHERE `itemId` = '%s'", itemTable, itemId));
            if (rs.next()) {
                permissionId = rs.getInt("id");
            }
            rs.close();
            statement.close();
            return permissionId;
        } catch (SQLException e) {
            plugin.getLogger().severe("无法获取PermissionId,请查看以下报错信息:");
            e.printStackTrace();
            return permissionId;
        }
    }

    public List<Integer> getRequestIds(String name) {
        List<Integer> list = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT `requestId` FROM `%s` WHERE `name` = '%s'", permissionTable, name));
            if (rs.next()) {
                list.add(rs.getInt("requestId"));
            }
            rs.close();
            statement.close();
            return list;
        } catch (SQLException e) {
            plugin.getLogger().severe("无法获取玩家请求数据,请查看以下报错信息:");
            e.printStackTrace();
            return list;
        }
    }

    public String getItemData(int permissionId) {
        String itemData = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT `item` FROM `%s` WHERE `id` = %d", itemTable, permissionId));
            if (rs.next()) {
                itemData = rs.getString("item");
            }
            rs.close();
            statement.close();
            return itemData;
        } catch (SQLException e) {
            plugin.getLogger().severe("无法获取ItemData,请查看以下报错信息:");
            e.printStackTrace();
            return itemData;
        }
    }

    public List<Integer> getNotResolveRequest() {
        List<Integer> list = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT `requestId` FROM `%s` WHERE `resolve` = 1", permissionTable));
            if (rs.next()) {
                list.add(rs.getInt("requestId"));
            }
            rs.close();
            statement.close();
            return list;
        } catch (SQLException e) {
            plugin.getLogger().severe("无法获取玩家请求数据,请查看以下报错信息:");
            e.printStackTrace();
            return list;
        }
    }

    public boolean checkHasItem(String itemId) {
        if (getPermissionId(itemId) == -1) {
            return false;
        }
        return true;
    }

    public void deleteItem(int permissionId) {
        try {
            Statement statement = conn.createStatement();
            String sql = String.format("DELETE FROM `%s` WHERE `id`=%d;", itemTable, permissionId);
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            plugin.getLogger().severe("删除请求数据失败,请查看以下报错信息:");
            e.printStackTrace();
        }
    }

    public void deleteRequest(int id) {
        try {
            Statement statement = conn.createStatement();
            String sql = String.format("DELETE FROM `%s` WHERE `id`=%d;", permissionTable, id);
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            plugin.getLogger().severe("删除请求数据失败,请查看以下报错信息:");
            e.printStackTrace();
        }
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