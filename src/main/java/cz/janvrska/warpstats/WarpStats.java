package cz.janvrska.warpstats;

import cz.janvrska.warpstats.commands.WStatsCommand;
import cz.janvrska.warpstats.config.Config;
import cz.janvrska.warpstats.database.DbConnector;
import cz.janvrska.warpstats.events.WarpEvent;
import cz.janvrska.warpstats.models.WarpModel;
import net.ess3.api.IEssentials;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Map;

public class WarpStats extends JavaPlugin {

    public IEssentials essentials;
    public WarpModel warpModel = new WarpModel();
    DbConnector dbConnector = new DbConnector();
    Config config = new Config();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Map<String, Object> configMap = getConfig().getValues(true);
        config.deserialize(configMap);

        dbConnector.createDataSource(config);

        try {
            if (config.database.type.text.equals("SQLITE")) {
                warpModel.createSQLiteDb();
            } else if (config.database.type.text.equals("MYSQL")) {
                warpModel.createDb();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        essentials = (IEssentials) getServer().getPluginManager().getPlugin("Essentials");
        getServer().getPluginManager().registerEvents(new WarpEvent(this), this);
        getCommand("wstats").setExecutor(new WStatsCommand(this));

    }

    @Override
    public void onDisable() {

    }
}
