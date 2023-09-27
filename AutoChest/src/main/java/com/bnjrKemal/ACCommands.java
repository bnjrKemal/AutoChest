package com.bnjrKemal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ACCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        /*

        /adminchest price <price> (elindeki öğenin fiyatını ayarlar)
        /adminchest locations
        /adminchest give (chest ver)
        /adminchest set (elimdekini chest olarak ayarla)
        /adminchest tp <player> <id>
        /adminchest reload

         */

        return false;
    }
}
