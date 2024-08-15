package org.peakim.service_request;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ServiceTaxi implements CommandExecutor {

    private Service_request instance = Service_request.getInstance(); //default hame
    private long lastRequestTime = 0;
    private Map<String, Long> playerRequestTimes = new HashMap<>(); // Time for request accept from taxi

    private Map<String, Long> chatCooldowns = new HashMap<>(); //

    private TaxiCall taxiCall;

    public ServiceTaxi(TaxiCall taxiCall) {
        this.taxiCall = taxiCall;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        //getConfig
        String taxi_requestsent = color(instance.getConfig().getString("taxi-request")); // taxi request player
        String taxi_receive = color(instance.getConfig().getString("taxi-receive"));
        //getConfig

        Player bazikon = (Player) sender;
        String name = bazikon.getName();

        //String Tabdil color va name
        String trequestsent_with_placeholder = taxi_requestsent.replace("%PLAYER%", bazikon.getPlayer().getName());
        String taxireceive_with_placeholder = taxi_receive.replace("%PLAYER%", bazikon.getPlayer().getName());


        if (sender instanceof Player) {
            String playerName = bazikon.getName();

            if (args.length > 0 && args[0].equalsIgnoreCase("accept")) {
                if (sender instanceof Player) {
                    Player accepter = (Player) sender;
                    if (accepter.hasPermission("taxi.getservice")) {
                        if (args.length > 1) {
                            String targetPlayerName = args[1];
                            Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
                            if (targetPlayer != null) {
                                playerRequestTimes.put(targetPlayerName, 0L);
                                accepter.sendMessage("Darkhaste " + targetPlayerName + " ghabool shod.");
                                targetPlayer.sendMessage("Darkhaste shoma ghabool shod.");
                                taxiCall.setPrivateChat(bazikon,targetPlayer);
                            } else {
                                accepter.sendMessage("Bazikoni ba in name peyda nashod.");
                            }
                        } else {
                            accepter.sendMessage("Lotfan name bazikon ra vared konid.");
                        }
                    }
                }
            }
            long lastRequestTime = playerRequestTimes.getOrDefault(playerName, 0L);

            if (System.currentTimeMillis() - lastRequestTime >= 60000) {
                bazikon.sendMessage(trequestsent_with_placeholder);
                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayers.hasPermission("taxi.getservice")) {
                        onlinePlayers.playSound(onlinePlayers.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                        onlinePlayers.sendMessage(taxireceive_with_placeholder);
                    }
                }
                playerRequestTimes.put(playerName, System.currentTimeMillis());
            } else {
                bazikon.sendMessage("Shoma hanooz nemitavanid darkhaste jadid ersal konid.");
            }
        } else {
            sender.sendMessage("Shoma hanooz nemitavanid darkhaste jadid ersal konid.");
        }
        return true;
    }



    private String color(String str){
        //CodeColor
        return ChatColor.translateAlternateColorCodes('&',str);
        //CodeColor
    }
}

