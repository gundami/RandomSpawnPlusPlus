package net.gundami.randomspawnplusplus.spawn;

import net.gundami.randomspawnplusplus.RandomSpawnPlusPlus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;


import java.util.*;

public class findSpawn {
    private RandomSpawnPlusPlus plugin;
    private FileConfiguration cfg;
    private int maxX,minX,maxZ,minZ;
    private List<Material> unsafeBlocks = new ArrayList<>();
    private List<Biome> excludedBiomes = new ArrayList<>();
    private Location spawnLocation;

    public findSpawn(RandomSpawnPlusPlus plugin){
        this.plugin= plugin;
        this.cfg=plugin.getConfig();

        this.maxX=cfg.getInt("spawn-area.maxX");
        this.minX=cfg.getInt("spawn-area.minX");
        this.maxZ=cfg.getInt("spawn-area.maxZ");
        this.minZ=cfg.getInt("spawn-area.minZ");

        Iterator iter = cfg.getStringList("unsafe-blocks").iterator();
        while(iter.hasNext()){
            this.unsafeBlocks.add(Material.getMaterial(String.valueOf(iter.next())));
        }
        iter = cfg.getStringList("excluded-biomes").iterator();
        while (iter.hasNext()){
            this.excludedBiomes.add(Biome.valueOf(String.valueOf(iter.next())));
        }
        if (cfg.getBoolean("prevent-water")){
            this.unsafeBlocks.add(Material.WATER);
        }
        if (cfg.getBoolean("prevent-lava")){
            this.unsafeBlocks.add(Material.LAVA);

        }
    }
    private int[] randomPoint(){
        int[] location = new int[5];
        location[0]=new Random().nextInt(maxX-minX);
        location[1]=new Random().nextInt(maxZ-minZ);
        return location;

    }
    public Location getSpawnPoint() {
        World wd = Bukkit.getWorld(cfg.getString("world"));
        boolean valid = false;

        while(!valid){
            int[] locationXZ=randomPoint();
            if(!unsafeBlocks.contains(wd.getHighestBlockAt(locationXZ[0], locationXZ[1]).getType()) && !excludedBiomes.contains(wd.getBiome(locationXZ[0], locationXZ[1]))){
                this.spawnLocation = new Location(wd, locationXZ[0], wd.getHighestBlockYAt(locationXZ[0], locationXZ[1]),locationXZ[1]);
                valid=true;

            }

        }
        return spawnLocation;

    }



}
