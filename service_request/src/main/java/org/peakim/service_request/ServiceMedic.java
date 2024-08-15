package org.peakim.service_request;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ServiceMedic implements CommandExecutor {


    private Service_request instance = Service_request.getInstance(); //default hame Config ha
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        //getConfig
        String medic_requestsent = color(instance.getConfig().getString("medic-request")); // medic request player
        String medic_receieve = color(instance.getConfig().getString("medic-receive"));
        //getConfig


        Player bazikon = (Player) sender;
        String name = bazikon.getName();

        //String Tabdil color va name
        String mrequestsent_with_placeholder = medic_requestsent.replace("%PLAYER%",bazikon.getPlayer().getName());
        String medicreceive_with_placeholder = medic_receieve.replace("%PLAYER%",bazikon.getPlayer().getName());

        if (sender instanceof Player){
            bazikon.sendMessage(mrequestsent_with_placeholder);
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()){

                if (onlinePlayers.hasPermission("Medic.getservice")) {

                        onlinePlayers.playSound(onlinePlayers.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                        onlinePlayers.sendMessage(medicreceive_with_placeholder);


                }



            }


        }


        return true;

        }

      private String color(String str) {


    //CodeColor
    return ChatColor.translateAlternateColorCodes('&', str);
    //CodeColor

    }}