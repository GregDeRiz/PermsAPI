package fr.gregderiz.permsAPI;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PermsPlugin extends JavaPlugin {
    private static PermsManager permsManager;

    @Override
    public void onEnable() {
        permsManager = new PermsManager(this);
        Bukkit.getPluginManager().registerEvents(new PermsListener(permsManager), this);
    }

    @Override
    public void onDisable() {
        if (permsManager != null) permsManager.destroy();
    }

    public static PermsManager getPermsManager() {
        return permsManager;
    }
}
