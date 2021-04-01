package net.gundami.randomspawnplusplus;

import net.ess3.api.IEssentials;
import net.gundami.randomspawnplusplus.conifg.ConfigFile;
import net.gundami.randomspawnplusplus.event.*;
import org.bukkit.plugin.java.JavaPlugin;


public class RandomSpawnPlusPlus extends JavaPlugin {

    private ConfigFile configManager;
    @Override
    public void onEnable(){
        getLogger().info("[RS++]Loading config");
        configManager = new ConfigFile(this, "config.yml");
        saveDefaultConfig();
        registerEvents();
        getLogger().info("[RS++]Finished");
    }
    @Override
    public void onDisable(){
        getLogger().info("[RS++]Disable RS++");


    }
    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new onJoin(this),this);
        getServer().getPluginCommand("rspp").setExecutor(new onCommand(this));


    }
    public IEssentials getEssentials() {
        return (IEssentials) getServer().getPluginManager().getPlugin("Essentials");
    }


}
