package xyz.swift.swiftqueueserver.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.swift.swiftqueueserver.SwiftQueueServer;
import xyz.swift.swiftqueueserver.manager.User;

public class JoinListener implements Listener {

    @EventHandler
    private void onPlayerJoin(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        SwiftQueueServer.getInstance().getPlayerManager().addUser(player);
        final User user = SwiftQueueServer.getInstance().getPlayerManager().getUser(player);
    }
}
