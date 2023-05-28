package me.z5882852.requestpermission.database;

import me.z5882852.requestpermission.RequestPermission;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        if (getRequestIds(playerName).contains(requestId) || requestId == -1) {
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
            while (rs.next()) {
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

    public String getCommand(int permissionId) {
        String command = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT `command` FROM `%s` WHERE `id` = %d", itemTable, permissionId));
            if (rs.next()) {
                command = rs.getString("command");
            }
            rs.close();
            statement.close();
            return command;
        } catch (SQLException e) {
            plugin.getLogger().severe("无法获取Command,请查看以下报错信息:");
            e.printStackTrace();
            return command;
        }
    }

    public List<Map<String, String>> getAllItemPermission() {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT `id`, `item`, `itemId`, `command` FROM `%s`", itemTable));
            while (rs.next()) {
                Map<String, String> requestInfo = new HashMap<>();
                requestInfo.put("id", rs.getString("id"));
                requestInfo.put("itemData", rs.getString("item"));
                requestInfo.put("itemId", rs.getString("itemId"));
                requestInfo.put("command", rs.getString("command"));
                list.add(requestInfo);
            }
            rs.close();
            statement.close();
            return list;
        } catch (SQLException e) {
            plugin.getLogger().severe("无法获取权限数据,请查看以下报错信息:");
            e.printStackTrace();
            return list;
        }
    }

    public List<Map<String, String>> getNotResolveRequest() {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT `id`, `name`, `date`, `requestId` FROM `%s` WHERE `resolve` = 0", permissionTable));
            while (rs.next()) {
                Map<String, String> requestInfo = new HashMap<>();
                requestInfo.put("id", rs.getString("id"));
                requestInfo.put("name", rs.getString("name"));
                requestInfo.put("date", rs.getString("date"));
                requestInfo.put("requestId", rs.getString("requestId"));
                list.add(requestInfo);
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

    public int getRequestId(int id) {
        int requestId = -1;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT `requestId` FROM `%s` WHERE `id` = %d", permissionTable, id));
            if (rs.next()) {
                requestId = rs.getInt("requestId");
            }
            rs.close();
            statement.close();
            return requestId;
        } catch (SQLException e) {
            plugin.getLogger().severe("无法获取Command,请查看以下报错信息:");
            e.printStackTrace();
            return requestId;
        }
    }

    public boolean setCommand(int permissionId, String newCommand) {
        try {
            Statement stmt = conn.createStatement();
            String sql = String.format("UPDATE `%s` SET `command`= `%s` WHERE `id` = %d;", itemTable, newCommand, permissionId);
            stmt.executeUpdate(sql);
            stmt.close();
            return true;
        } catch (SQLException e) {
            plugin.getLogger().severe("无法更新Command,请查看以下报错信息:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean setCommand(String itemId, String newCommand) {
        try {
            Statement stmt = conn.createStatement();
            String sql = String.format("UPDATE `%s` SET `command`= '%s' WHERE `itemId` = '%s';", itemTable, newCommand, itemId);
            stmt.executeUpdate(sql);
            stmt.close();
            return true;
        } catch (SQLException e) {
            plugin.getLogger().severe("无法更新Command,请查看以下报错信息:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkHasItem(String itemId) {
        if (getPermissionId(itemId) == -1) {
            return false;
        }
        return true;
    }

    public void resolveRequest(int id) {
        try {
            Statement stmt = conn.createStatement();
            String sql = String.format("UPDATE `%s` SET `resolve`= 1 WHERE `id` = %d;", permissionTable, id);
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            plugin.getLogger().severe("无法更新玩家Resolve,请查看以下报错信息:");
            e.printStackTrace();
        }
    }

    public boolean deleteItem(String itemId) {
        try {
            Statement statement = conn.createStatement();
            String sql = String.format("DELETE FROM `%s` WHERE `itemId`= '%s';", itemTable, itemId);
            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch (SQLException e) {
            plugin.getLogger().severe("删除权限数据失败,请查看以下报错信息:");
            e.printStackTrace();
            return false;
        }
    }


    public void deleteItem(int permissionId) {
        try {
            Statement statement = conn.createStatement();
            String sql = String.format("DELETE FROM `%s` WHERE `id` = %d;", itemTable, permissionId);
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            plugin.getLogger().severe("删除权限数据失败,请查看以下报错信息:");
            e.printStackTrace();
        }
    }

    public void deleteRequest(int id) {
        try {
            Statement statement = conn.createStatement();
            String sql = String.format("DELETE FROM `%s` WHERE `id` = %d;", permissionTable, id);
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