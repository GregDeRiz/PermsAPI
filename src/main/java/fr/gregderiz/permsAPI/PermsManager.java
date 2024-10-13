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
        if (!isPlayerRegistered(player)) return false;
        return getPermissionsByPlayer(player).contains(permission);
    }

    public void register(Player player) {
        if (isPlayerRegistered(player)) return;

        this.permissions.put(player.getUniqueId(), Sets.newHashSet());
    }

    public void addPermission(Player player, String permission) {
        if (hasPermission(player, permission)) return;

        getPermissionsByPlayer(player).add(permission);
    }

    public void removePermission(Player player, String permission) {
        if (!hasPermission(player, permission)) return;

        getPermissionsByPlayer(player).remove(permission);
    }

    public void unRegister(Player player) {
        this.permissions.remove(player.getUniqueId());
    }

    public Map<UUID, Set<String>> getPermissions() {
        return this.permissions;
    }

    public Set<String> getPermissionsByPlayer(Player player) {
        return this.permissions.get(player.getUniqueId());
    }

    public void destroy() {
        for (UUID uuid : this.permissions.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !player.isOnline()) continue;

            unRegister(player);
        }
    }
}
