package xyz.swift.swiftqueueserver.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.swift.swiftqueueserver.SwiftQueueServer;
import xyz.swift.swiftqueueserver.manager.User;
import xyz.swift.swiftqueueserver.manager.queue.Queue;

public class QuitListener implements Listener {

    @EventHandler
    private void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final User user = SwiftQueueServer.getInstance().getPlayerManager().getUser(player);

        if (user.getPosition() > 0) {
            final Queue queue = SwiftQueueServer.getInstance().getQueueManager().getQueue(user.getQueue());
            queue.removeQueue(player);
            updateQueuePositions(queue, user.getPosition());
        }

        SwiftQueueServer.getInstance().getPlayerManager().removeUser(player);
    }

    private void updateQueuePositions(final Queue queue, final int startingPosition) {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            final User onlineUser = SwiftQueueServer.getInstance().getPlayerManager().getUser(onlinePlayer);
            if (onlineUser.getPosition() > 1 && onlineUser.getPosition() >= startingPosition && onlineUser.getQueue().equalsIgnoreCase(queue.getQueueServer())) {
                final int newPosition = onlineUser.getPosition() - 1;
                onlineUser.setPosition(newPosition);
                onlinePlayer.sendMessage(formatQueueMessage(queue, newPosition));
            }
        });
    }

    private String formatQueueMessage(final Queue queue, final int position) {
        return SwiftQueueServer.getInstance().getConfiguration().prefix +
                SwiftQueueServer.getInstance().getConfiguration().queueMove
                        .replace("%position%", String.valueOf(position))
                        .replace("%maxpos%", String.valueOf(queue.getTotalQueue().size()));
    }
}
