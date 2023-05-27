package me.z5882852.requestpermission.database;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;

public class MySQLTest {
    private JavaPlugin plugin;
    private FileConfiguration config;
    private Connection conn;
    private String itemTable;
    private String permissionTable;

    public MySQLTest(JavaPlugin plugin) {
        this.plugin = plugin;
        config = plugin.getConfig();

        try {
            plugin.getLogger().info("数据库连接测试...");
            String username = config.getString("mysql.user");
            String password = config.getString("mysql.password");
            itemTable = config.getString("mysql.item_table");
            permissionTable = config.getString("mysql.permission_table");
            String url = "jdbc:mysql://" + config.getString("mysql.host") + ":" + config.getString("mysql.port") + "/" + config.getString("mysql.database") + config.getString("mysql.params");
            this.conn = DriverManager.getConnection(url, username, password);
            // 检查表是否存在，如果不存在则创建
            if (!isTableExists(itemTable)) {
                plugin.getLogger().info("检测到" + itemTable +"表不存在，正在创建新的表...");
                createItemTable(itemTable);
                plugin.getLogger().info("数据表创建成功!");
            }
            if (!isTableExists(permissionTable)) {
                plugin.getLogger().info("检测到" + permissionTable +"表不存在，正在创建新的表...");
                createPermissionTable(permissionTable);
                plugin.getLogger().info("数据表创建成功!");
            }
            conn.close();
            plugin.getLogger().info("数据库连接成功!");
        } catch (SQLException e) {
            plugin.getLogger().severe("无法连接数据库: " + e.getMessage());
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    private boolean isTableExists(String tableName) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = String.format("SELECT COUNT(*) FROM information_schema.TABLES WHERE TABLE_NAME = '%s'", tableName);
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        int count = resultSet.getInt(1);
        resultSet.close();
        statement.close();

        return count > 0;
    }

    private void createItemTable(String tableName) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = String.format("CREATE TABLE `%s` (", tableName) +
                "  `id` int NOT NULL AUTO_INCREMENT," +
                "  `item` text NOT NULL," +
                "  `itemId` text NOT NULL," +
                "  `command` text NOT NULL," +
                "  PRIMARY KEY (`id`)" +
                ") ENGINE = innodb";
        statement.executeUpdate(sql);
        statement.close();
    }

    private void createPermissionTable(String tableName) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = String.format("CREATE TABLE `%s` (", tableName) +
                "  `id` int NOT NULL AUTO_INCREMENT," +
                "  `name` text NOT NULL," +
                "  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "  `requestId` int NOT NULL," +
                "  `resolve` int (0) NOT NULL DEFAULT 0," +
                "  PRIMARY KEY (`id`)" +
                ") ENGINE = innodb";
        statement.executeUpdate(sql);
        statement.close();
    }
}
