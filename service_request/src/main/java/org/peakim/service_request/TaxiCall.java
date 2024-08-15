package org.peakim.service_request;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;

public class TaxiCall extends JavaPlugin implements Listener {

    private Map<String, Long> chatCooldowns = new HashMap<>();
    private static final long CHAT_DURATION = 5 * 60 * 1000;

    public void setPrivateChat(Player player1, Player player2) {
        long endTime = System.currentTimeMillis() + CHAT_DURATION;
        chatCooldowns.put(player1.getName(), endTime);
        chatCooldowns.put(player2.getName(), endTime);
    }

    public boolean isInPrivateChat(Player player) {
        long endTime = chatCooldowns.getOrDefault(player.getName(), 0L);
        return System.currentTimeMillis() <= endTime;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();
        String message = event.getMessage();
        String prefix = "&7[&6Taxi &aCall&7] ";

        if (isInPrivateChat(sender)) {
            Player girande = null;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player != sender && isInPrivateChat(player)) {
                    girande = player;
                    break;
                }
            }

            if (girande != null) {
                event.setFormat(prefix + "%s: %s");
                girande.sendMessage(prefix + sender.getName() + ": " + message);
                event.setCancelled(true);
                return;
            }
        }

        event.setCancelled(true);
        sender.sendMessage("Shoma dar hal hazer dar chati private ba bazikon-e digar hastid.");
    }
}