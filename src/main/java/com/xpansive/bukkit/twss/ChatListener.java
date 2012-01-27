package com.xpansive.bukkit.twss;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatListener implements Listener {
    private final TwssJsClient client;
    private final JavaPlugin plugin;

    public ChatListener(ThatsWhatSheSaid plugin) {
        client = new TwssJsClient(ThatsWhatSheSaid.config.server, ThatsWhatSheSaid.config.algorithm,
                ThatsWhatSheSaid.config.threshold, ThatsWhatSheSaid.config.numWordsInNgram, ThatsWhatSheSaid.config.numNeighbours);
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(final PlayerChatEvent event) {
        if (!event.isCancelled()) {
            // Run the check in another thread, then display the message in the server thread.
            new Thread(new Runnable() {
                public void run() {
                    if (client.is(event.getMessage())) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {
                                String text = TextVarReplacer.replace(
                                        ThatsWhatSheSaid.config.message,
                                        new TextVarReplacer.Variable("user", event.getPlayer().getDisplayName()),
                                        new TextVarReplacer.Variable("message", event.getMessage()));
                                Bukkit.broadcastMessage(text);
                            }
                        });
                    }
                }
            }).start();
        }
    }
}
