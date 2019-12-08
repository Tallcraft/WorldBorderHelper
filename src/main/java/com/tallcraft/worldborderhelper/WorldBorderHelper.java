package com.tallcraft.worldborderhelper;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldBorderHelper extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }


    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(!sender.hasPermission("worldborderhelper.setborder")) {
            sender.sendMessage(cmd.getPermissionMessage());
            return true;
        }
        if(args.length != 2) {
            return false;
        }

        // Get world by name string
        String worldName = args[0];
        World world = Bukkit.getWorld(worldName);
        if(world == null) {
            sender.sendMessage("Invalid world '" + worldName + "'!");
            return false;
        }

        // Get diameter number
        double diameter;
        try {
            diameter = Double.parseDouble(args[1]);
        } catch (NumberFormatException ex) {
            sender.sendMessage("Invalid size argument. Requires number.");
            return false;
        }

        // Get and update world border by setting the diameter centered on world spawn.
        WorldBorder border = world.getWorldBorder();
        border.setCenter(world.getSpawnLocation());
        border.setSize(diameter);

        String message = "Border of world '" + worldName + "' set to " + diameter + " in diameter.";
        getLogger().info(message);
        sender.sendMessage(message);

        return true;
    }
}
