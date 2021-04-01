package net.gundami.randomspawnplusplus.conifg;

import net.gundami.randomspawnplusplus.RandomSpawnPlusPlus;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class ConfigFile {
    private FileConfiguration config;
    private RandomSpawnPlusPlus plugin;
    private String fileName;
    public ConfigFile(RandomSpawnPlusPlus plugin, String fileName) {
        this.fileName = fileName;
        this.plugin = plugin;
        reload();
    }
    public void reload() {
        File ConfigFile = createFile();
        config = new YamlConfiguration();
        try {
            config.load(ConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    private File createFile() {
        File ConfigFile = new File(plugin.getDataFolder(), fileName);
        if (!ConfigFile.exists()) {
            plugin.getLogger().info("[RS++]No config file, generate a new one");
            ConfigFile.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
        }

        return ConfigFile;


    }




}
