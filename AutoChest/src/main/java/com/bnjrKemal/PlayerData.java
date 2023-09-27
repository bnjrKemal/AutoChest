package com.bnjrKemal;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {

    private UUID uuid;
    private List<Location> locations;
    private Double puddle = 0.0;

    public PlayerData(UUID uuid){
        this.uuid = uuid;
        locations = new ArrayList<>();
        if(AutoChest.getDataYaml().get(uuid + ".locations") != null)
            locations = (List<Location>) AutoChest.getDataYaml().getList(uuid + ".locations");
        if(AutoChest.getDataYaml().get(uuid + ".puddle") != null)
            puddle = AutoChest.getDataYaml().getDouble(uuid + ".puddle");
    }

    public Double getPuddle() {
        return puddle;
    }

    public void addPuddle(Double puddle) {
        this.puddle += puddle;
    }

    public void transferPuddle() {
        AutoChest.getEconomy().depositPlayer(Bukkit.getOfflinePlayer(uuid), puddle);
        puddle = 0.0;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void addLocation(Location location) {
        this.locations.add(location);
    }

    public void removeLocation(Location location) {
        this.locations.remove(location);
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public void save(){
        AutoChest.getDataYaml().set(uuid + ".locations", locations);
        AutoChest.getDataYaml().set(uuid + ".puddle", puddle);
        try {
            AutoChest.getDataYaml().save(AutoChest.autoChest.getDataFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
