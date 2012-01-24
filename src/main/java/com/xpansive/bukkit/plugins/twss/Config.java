package com.xpansive.bukkit.plugins.twss;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public String server;
    public String algorithm;
    public double threshold;
    public int numNeighbours;
    public int numWordsInNgram;
    public String message;

    public Config(FileConfiguration config) {
        server = config.getString("server");
        algorithm = config.getString("algorithm");
        threshold = config.getDouble("threshold");
        numNeighbours = config.getInt("numNeighbours");
        numWordsInNgram = config.getInt("numWordsInNgram");
        message = config.getString("message");
    }
}
