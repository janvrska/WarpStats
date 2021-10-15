package cz.janvrska.warpstats.database;

import com.zaxxer.hikari.HikariConfig;
import cz.janvrska.warpstats.config.Config;

import java.sql.Connection;
import java.sql.SQLException;

public class DbConnector {

    private static DataSource dataSource;
    private HikariConfig hikariConfig = new HikariConfig();

    public void createDataSource(Config config) {
        if (config.database.type.equals(ConnectionType.valueOf("MYSQL"))){
            hikariConfig.setJdbcUrl("jdbc:mysql://"+config.database.host+":"+config.database.port+"/"+config.database.database);
            hikariConfig.setUsername(config.database.username);
            hikariConfig.setPassword(config.database.password);
            hikariConfig.setConnectionTimeout(5000);
            hikariConfig.setMaxLifetime(60*30*1000);
        } else {
            hikariConfig.setJdbcUrl("jdbc:sqlite:plugins/WarpStats/warpstats.db");
        }
        dataSource = new DataSource(hikariConfig);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
