package com.xpansive.bukkit.twss;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ThatsWhatSheSaid extends JavaPlugin {
    public static Config config;

    public void onDisable() {
        System.out.println(this + " is now disabled!");
    }

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        config = new Config(getConfig());
        Bukkit.getServer().getPluginManager().registerEvents(new ChatListener(this), this);

        System.out.println(this + " is now enabled!");
    }
}
