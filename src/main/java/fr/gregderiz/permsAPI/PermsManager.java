package fr.gregderiz.permsAPI;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public final class PermsManager {
    private final Map<UUID, String> permissions;

    public PermsManager() {
        this.permissions = Maps.newHashMap();
    }

    public boolean hasPermission(Player player, String permission) {
        if (!this.permissions.containsKey(player.getUniqueId())) return false;
        return this.permissions.get(player.getUniqueId()).equalsIgnoreCase(permission);
    }

    public void addPermission(Player player, String permission) {
        if (hasPermission(player, permission)) return;

        this.permissions.put(player.getUniqueId(), permission);
    }

    public void removePermission(Player player, String permission) {
        if (!hasPermission(player, permission)) return;

        this.permissions.remove(player.getUniqueId(), permission);
    }

    public void removePermissions(Player player) {
        this.permissions.remove(player.getUniqueId());
    }

    public Map<UUID, String> getPermissions() {
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
