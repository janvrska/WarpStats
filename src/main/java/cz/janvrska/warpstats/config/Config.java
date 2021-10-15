package cz.janvrska.warpstats.config;

import cz.janvrska.warpstats.database.ConnectionType;

import java.util.Map;

public class Config {
    public DatabaseConfig database;

    public Config deserialize(Map<String, Object> configMap) {
        if (database == null) {
            database = new DatabaseConfig();
        }

        database.type = (ConnectionType.valueOf((String) configMap.get("config.database.type")));
        database.host = (String) configMap.get("config.database.host");
        database.database = (String) configMap.get("config.database.database");
        database.port = (int) configMap.get("config.database.port");
        database.username = (String) configMap.get("config.database.username");
        database.password = (String) configMap.get("config.database.password");

        return this;
    }
}
