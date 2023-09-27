package com.bnjrKemal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ACListeners implements Listener {

    @EventHandler
    public void onInvMoveEvent(InventoryMoveItemEvent e){

        if(AutoChest.getPlayerData(e.getDestination().getLocation()) == null) return;
        if(AutoChest.getPricesYaml().get(e.getItem().getType().toString()) == null) return;
        PlayerData playerData = AutoChest.getPlayerData(e.getDestination().getLocation());
        double puddle = e.getItem().getAmount() * AutoChest.getPricesYaml().getDouble(e.getItem().getType().toString());
        e.getDestination().remove(e.getItem());           //e.getInitiator().remove(e.getItem());
        playerData.addPuddle(puddle);
        //hologram

    }

    @EventHandler
    public void onOpenChest(PlayerInteractEvent e){
        if(e.getInteractionPoint() == null) return;
        if(AutoChest.getPlayerData(e.getInteractionPoint()) == null) return;
        if(!e.getPlayer().isSneaking()) return;
        PlayerData playerData = AutoChest.getPlayerData(e.getInteractionPoint());
        if(playerData.getPuddle() > 0.0)
            e.setCancelled(true);
            playerData.transferPuddle();
            //sendMessage hesabına para geldi
    }

    @EventHandler
    public void onPlaceChest(BlockPlaceEvent e){
        if(!e.getPlayer().getItemOnCursor().equals(AutoChest.autoChest.getConfig().getItemStack("item"))) return;
        PlayerData playerData = AutoChest.getPlayerData(e.getPlayer().getUniqueId());
        playerData.addLocation(e.getBlock().getLocation());
        //sendMessage konuldu
    }

    @EventHandler
    public void onBreakChest(BlockBreakEvent e){
        if(AutoChest.getPlayerData(e.getBlock().getLocation()) == null) return;
        if(!AutoChest.getPlayerData(e.getPlayer().getLocation()).getPlayer().getUniqueId().equals(e.getPlayer().getUniqueId())){
            if(e.getPlayer().isOp()){
                //admin kırdı
                return;
            }
            //sandığın sahibi kırabilir
            e.setCancelled(true);
            return;
        }
        PlayerData playerData = AutoChest.getPlayerData(e.getBlock().getLocation());
        playerData.transferPuddle();
        playerData.removeLocation(e.getBlock().getLocation());
        //birikinti hesabına aktarıldı ve kaldırıldı.
    }

}
