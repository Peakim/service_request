package org.peakim.service_request;

import org.bukkit.plugin.java.JavaPlugin;
import org.peakim.service_request.commands.reload;

public final class Service_request extends JavaPlugin {

    private TaxiCall taxiCall;

    public static Service_request instance;
    public static Service_request getInstance(){

        return instance;

    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig(); //config
        instance = this; //gereftan instance

        taxiCall = new TaxiCall();

            // ثبت دستور کامند ها
            getCommand("servicerequest-reload").setExecutor(new reload());

            getCommand("medic").setExecutor(new ServiceMedic());
            getCommand("taxi").setExecutor(new ServiceTaxi(taxiCall));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
