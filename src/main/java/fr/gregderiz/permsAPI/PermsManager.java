package fr.gregderiz.permsAPI;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class PermsManager {
    private final Plugin plugin;
    private final Map<UUID, PermissionAttachment> permissions;

    public PermsManager(Plugin plugin) {
        this.plugin = plugin;
        this.permissions = Maps.newHashMap();
    }

    public boolean isRegistered(Player player) {
        return this.permissions.containsKey(player.getUniqueId());
    }

    public void register(Player player) {
        if (isRegistered(player)) return;

        PermissionAttachment attachment = player.addAttachment(this.plugin);
        this.permissions.put(player.getUniqueId(), attachment);
    }

    public void unRegister(Player player) {
        if (!isRegistered(player)) return;

        PermissionAttachment attachment = this.permissions.get(player.getUniqueId());
        removePermissions(player);
        player.removeAttachment(attachment);
        this.permissions.remove(player.getUniqueId());
    }

    public boolean hasPermission(Player player, String permission) {
        return getAttachment(player).getPermissible().hasPermission(permission);
    }

    public void addPermission(Player player, String permission) {
        if (hasPermission(player, permission)) return;

        getAttachment(player).setPermission(permission, true);
    }

    public void removePermission(Player player, String permission) {
        if (!hasPermission(player, permission)) return;

        getAttachment(player).unsetPermission(permission);
    }

    public void removePermissions(Player player) {
        Set<PermissionAttachmentInfo> permissionInfos = Sets.newHashSet(player.getEffectivePermissions());
        for (PermissionAttachmentInfo permissionInfo : permissionInfos) {
            String permission = permissionInfo.getPermission();
            getAttachment(player).unsetPermission(permission);
        }
    }

    public Map<UUID, PermissionAttachment> getPermissions() {
        return this.permissions;
    }

    public PermissionAttachment getAttachment(Player player) {
        if (!isRegistered(player)) register(player);
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
