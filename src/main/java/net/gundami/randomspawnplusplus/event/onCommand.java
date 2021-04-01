package net.gundami.randomspawnplusplus.event;

import net.gundami.randomspawnplusplus.RandomSpawnPlusPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class onCommand implements CommandExecutor {
    private RandomSpawnPlusPlus plugin;
    private List<String> subCommand = new ArrayList<>();
    public onCommand(RandomSpawnPlusPlus plugin){
        this.plugin=plugin;
        subCommand.add("reload");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args){
        if (sender instanceof Player){
            if (args.length ==1 && args[0].equals("reload")){
                if (sender.hasPermission("rspp.reload")){
                    plugin.reloadConfig();
                    ((Player) sender).sendMessage("[RS++]Config file reload");
                    return true;
                }
                ((Player) sender).sendMessage("[RS++]NO PERMISSION");
            }
            ((Player) sender).sendMessage("[RS++]Unknown args");
            return true;
        }else{
            if (args.length ==1 && args[0].equals("reload")){
                plugin.reloadConfig();
                plugin.getLogger().info("[RS++]Config file reload");
                return true;

            }
            plugin.getLogger().info("[RS++]Unknown args");
            return true;
        }



    }


}
