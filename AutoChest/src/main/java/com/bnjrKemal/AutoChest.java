package com.bnjrKemal;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class AutoChest extends JavaPlugin {

    private static Economy econ = null;

    public static AutoChest autoChest;

    public static List<PlayerData> playerDataList = new ArrayList<>();
    File dataFile;
    File pricesFile;
    static YamlConfiguration dataYaml;
    static YamlConfiguration pricesYaml;

    @Override
    public void onEnable() {

        if (!setupEconomy() ) {
            System.out.println("[AutoChets] You must have to install Vault plugin. That plugin has been disabled!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        autoChest = this;
        PlayerData playerData = new PlayerData(UUID.randomUUID());
        playerDataList.add(playerData);
        loadData();

        Bukkit.getPluginManager().registerEvents(new ACListeners(), this);
        getCommand("autochest").setExecutor(new ACCommands());

    }

    @Override
    public void onDisable() {
        for(PlayerData save : playerDataList)
            save.save();
    }

    private void loadData() {
        dataFile = new File(getDataFolder(), "data.yml");
        if(dataFile.exists()){
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        dataYaml = YamlConfiguration.loadConfiguration(dataFile);

        pricesFile = new File(getDataFolder(), "prices.yml");
        if(pricesFile.exists()){
            try {
                pricesFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        pricesYaml = YamlConfiguration.loadConfiguration(pricesFile);
    }

    public static PlayerData getPlayerData(Location location) {
        for(PlayerData playerDat : playerDataList)
            if(playerDat.getLocations().contains(location)) return playerDat;
        return null;
    }

    public static PlayerData getPlayerData(UUID uuid){
        for(PlayerData playerDat : playerDataList)
            if(playerDat.getPlayer().getUniqueId().equals(uuid)) return playerDat;
        PlayerData playerData = new PlayerData(uuid);
        return playerData;
    }

    public OfflinePlayer getOwner(PlayerData playerData) {
        return playerData.getPlayer();
    }

    public static YamlConfiguration getDataYaml() {
        return dataYaml;
    }

    public File getDataFile() {
        return dataFile;
    }

    public File getPricesFile() {
        return pricesFile;
    }

    public static YamlConfiguration getPricesYaml() {
        return pricesYaml;
    }

    //Vault =========================================================================================================
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public static Economy getEconomy() {
        return econ;
    }
    //Vault =========================================================================================================
}
