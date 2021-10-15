package cz.janvrska.warpstats.commands;

import cz.janvrska.warpstats.WarpStats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class WStatsCommand implements CommandExecutor {

    private final WarpStats plugin;

    public WStatsCommand(WarpStats warpStats) {
        this.plugin = warpStats;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(
                    "/wstats - Show Warp statistics \n"
            );
        }
        return true;
    }
}
