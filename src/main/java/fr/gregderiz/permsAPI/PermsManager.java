package fr.gregderiz.permsAPI;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class PermsManager {
    private final Map<UUID, Set<String>> permissions;

    public PermsManager() {
        this.permissions = Maps.newHashMap();
    }

    public boolean isPlayerRegistered(Player player) {
        return this.permissions.containsKey(player.getUniqueId());
    }

    public boolean hasPermission(Player player, String permission) {
        return this.permissions.get(player.getUniqueId()).contains(permission);
    }

    public void addPermission(Player player, String permission) {
        if (!this.isPlayerRegistered(player)) this.permissions.put(player.getUniqueId(), Sets.newHashSet());
        if (hasPermission(player, permission)) return;

        this.permissions.get(player.getUniqueId()).add(permission);
    }

    public void removePermission(Player player, String permission) {
        if (!this.isPlayerRegistered(player)) return;
        if (!hasPermission(player, permission)) return;

        this.permissions.get(player.getUniqueId()).remove(permission);
    }

    public void removePermissions(Player player) {
        this.permissions.remove(player.getUniqueId());
    }

    public Map<UUID, Set<String>> getPermissions() {
        return this.permissions;
    }

    public void destroy() {
        for (UUID uuid : this.permissions.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !player.isOnline()) continue;

            removePermissions(player);
        }
    }
}
