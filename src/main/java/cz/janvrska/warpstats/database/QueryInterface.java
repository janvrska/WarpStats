package cz.janvrska.warpstats.database;

import java.sql.SQLException;

public interface QueryInterface {
    void insertEvent(String warpName, String playerName) throws SQLException;

    Integer createWarp(String warpName) throws SQLException;

    Integer findWarpIdByName(String warpName) throws SQLException;
}
