package fr.gregderiz.permsAPI;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PermsPlugin extends JavaPlugin {
    private PermsManager permsManager;

    @Override
    public void onDisable() {
        if (this.permsManager != null) this.permsManager.destroy();
    }

    public PermsPlugin register() {
        this.permsManager = new PermsManager(this);
        Bukkit.getPluginManager().registerEvents(new PermsListener(this.permsManager), this);
        return this;
    }

    public PermsManager getPermsManager() {
        return this.permsManager;
    }
}
