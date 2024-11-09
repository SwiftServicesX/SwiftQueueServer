package xyz.swift.swiftqueueserver.manager;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class UserManager {
    private final Set<User> users;

    public UserManager() {
        this.users = new HashSet<>();
    }

    public User getUser(final Player player) {
        return this.getUser(player.getUniqueId());
    }

    public User getUser(final UUID uuid) {
        return this.users.stream().filter(user -> user.getUUID().equals(uuid)).findAny().orElse(null);
    }

    public void addUser(final Player player) {
        User user = new User(player.getUniqueId(), player.getName());
        this.users.add(user);
    }

    public void removeUser(final Player player) {
        this.users.remove(this.getUser(player));
    }
}
