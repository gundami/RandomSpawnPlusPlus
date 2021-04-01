package net.gundami.randomspawnplusplus.event;

import com.earth2me.essentials.User;
import net.gundami.randomspawnplusplus.RandomSpawnPlusPlus;
import net.gundami.randomspawnplusplus.spawn.findSpawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;


import java.util.ArrayList;


public class onJoin implements Listener {

    private RandomSpawnPlusPlus plugin;
    private FileConfiguration cfg;

    private static ArrayList<String> firstJoinPlayers = new ArrayList<>();

    public onJoin(RandomSpawnPlusPlus plugin){
        this.plugin=plugin;
        this.cfg=plugin.getConfig();

    }

    @EventHandler
    public void preLoginHandler(AsyncPlayerPreLoginEvent event) {
        if (cfg.getBoolean("on-first-join")) {

                String playerName = event.getName();
                boolean hasPlayed = Bukkit.getServer().getOfflinePlayer(playerName).hasPlayedBefore();

                if (!hasPlayed) {
                    firstJoinPlayers.add(playerName);
                }

        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void firstJoinHandler(PlayerJoinEvent event) {

        Player player = event.getPlayer();

            if (cfg.getBoolean("on-first-join")) {
                if (firstJoinPlayers.contains(player.getName())) {
                    Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                        Bukkit.getScheduler().runTask(this.plugin, ()-> {

                                findSpawn fp = new findSpawn(plugin);
                                Location spawnLocation = fp.getSpawnPoint();
                                if (cfg.getBoolean("sethome-at-rtp")) {
                                    User user = plugin.getEssentials().getUser(player);
                                    user.setHome("home",spawnLocation);
                                    user.save();
                                    plugin.getLogger().info("[RS++]Set player's home");

                                }
                                plugin.getLogger().info("[RS++]Teleport player to "+spawnLocation.getBlockX()+","+spawnLocation.getBlockY()+","+spawnLocation.getBlockZ());
                                player.teleport(spawnLocation);

                        });
                    });

                        firstJoinPlayers.remove(player.getName());

                }
            }
    }
}
