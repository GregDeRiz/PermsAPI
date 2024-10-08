package fr.gregderiz.permsAPI;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PermsListener implements Listener {
    private final PermsManager permsManager;

    public PermsListener(PermsManager permsManager) {
        this.permsManager = permsManager;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void PlayerJoin(PlayerJoinEvent event) {
        this.permsManager.register(event.getPlayer());
        this.permsManager.removePermissions(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void PlayerLeave(PlayerQuitEvent event) {
        this.permsManager.unRegister(event.getPlayer());
    }
}
