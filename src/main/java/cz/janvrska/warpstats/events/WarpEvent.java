package cz.janvrska.warpstats.events;

import com.earth2me.essentials.commands.WarpNotFoundException;
import cz.janvrska.warpstats.WarpStats;
import net.ess3.api.InvalidWorldException;
import net.ess3.api.events.UserWarpEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.SQLException;

public class WarpEvent implements Listener {

    WarpStats plugin;

    public WarpEvent(WarpStats warpStats) {
        this.plugin = warpStats;
    }

    @EventHandler
    public void onWarp(UserWarpEvent event) {
        if (!event.isCancelled()) {
            final String user = event.getUser().getName();
            final String warp = event.getWarp();
            boolean exist = true;

            try {
                plugin.essentials.getWarps().getWarp(warp);
            } catch (WarpNotFoundException | InvalidWorldException e) {
                exist = false;
            }
            if (exist) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            plugin.warpModel.insertEvent(warp, user);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
