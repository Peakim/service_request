package org.peakim.service_request.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.peakim.service_request.Service_request;

public class reload implements CommandExecutor {
    private Service_request instance = Service_request.getInstance();

    @Override

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        Player bazikon = (Player) sender; // بازیکن

        if (sender instanceof Player){

            if (sender.hasPermission("servicerequest.reload")) {
                instance.reloadConfig();
                sender.sendMessage("Service-request has been reloaded!");

            }
        }



        return true;
    }


}

