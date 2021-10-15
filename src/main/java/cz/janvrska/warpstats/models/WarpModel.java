package cz.janvrska.warpstats.models;

import cz.janvrska.warpstats.database.DbConnector;
import cz.janvrska.warpstats.database.QueryInterface;

import java.sql.*;

public class WarpModel implements QueryInterface {

    public void insertEvent(String warp, String playerName) throws SQLException {
        Integer warpId = findWarpIdByName(warp);
        if (warpId == null) {
            warpId = createWarp(warp);
        }
        try (Connection connection = DbConnector.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO warp_events (player_name,warp_id) VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            if (warpId != null) {
                ps.setString(1, playerName);
                ps.setInt(2, warpId);
                ps.executeUpdate();
            }
        }
    }

    public Integer findWarpIdByName(String name) throws SQLException {
        try (Connection connection = DbConnector.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM warps WHERE name='" + name + "';");
            ps.execute();
            ResultSet result = ps.getResultSet();

            if (!result.next()) {
                return null;
            }

            return result.getInt("id");
        }
    }

    public Integer createWarp(String name) throws SQLException {
        try (Connection connection = DbConnector.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO warps (name) VALUES (?);",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, name);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return null;
        }
    }

    public void createDb() throws SQLException {
        try (Connection connection = DbConnector.getConnection()) {
            Statement statement = connection.createStatement();
            final String sql =
                    "CREATE TABLE IF NOT EXISTS warps (" +
                            "id int(11) NOT NULL AUTO_INCREMENT," +
                            "name varchar(65) NOT NULL," +
                            "PRIMARY KEY (id)," +
                            "KEY name (name)" +
                            ");";
            statement.execute(sql);

            final String sql2 =
                    "CREATE TABLE IF NOT EXISTS warp_events (" +
                            "id int(11) NOT NULL AUTO_INCREMENT," +
                            "player_name varchar(20) NOT NULL," +
                            "warp_id int(11) NOT NULL," +
                            "datetime datetime NOT NULL DEFAULT current_timestamp()," +
                            "PRIMARY KEY (id)," +
                            "KEY warp_id (warp_id)," +
                            "KEY player_name (player_name)," +
                            "CONSTRAINT warp_events_ibfk_1 FOREIGN KEY (warp_id) REFERENCES warps (id) ON DELETE CASCADE ON UPDATE CASCADE" +
                            ");";
            statement.execute(sql2);
        }
    }

    public void createSQLiteDb() throws SQLException {
        try (Connection connection = DbConnector.getConnection()) {
            Statement statement = connection.createStatement();
            final String sql =
                    "CREATE TABLE IF NOT EXISTS warps" +
                            "(" +
                            "    id   INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "    name varchar(65) NOT NULL" +
                            ");";
            statement.execute(sql);

            final String sql2 =
                    "CREATE TABLE IF NOT EXISTS warp_events" +
                            "(" +
                            "    id          INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "    player_name varchar(20) NOT NULL," +
                            "    warp_id     int(11)     NOT NULL," +
                            "    datetime    datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                            ");";
            statement.execute(sql2);
        }
    }
}
