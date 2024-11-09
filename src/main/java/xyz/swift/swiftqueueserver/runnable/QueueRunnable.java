package xyz.swift.swiftqueueserver.runnable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.swift.swiftqueueserver.SwiftQueueServer;
import xyz.swift.swiftqueueserver.bungee.BungeeCord;
import xyz.swift.swiftqueueserver.manager.User;
import xyz.swift.swiftqueueserver.manager.queue.Queue;

import java.util.List;

public class QueueRunnable {

    public static void queueLoop(final List<String> servers) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (SwiftQueueServer.getInstance().getConfiguration().system) {
                    processQueues(servers);
                }
            }
        }.runTaskTimerAsynchronously(SwiftQueueServer.getInstance(), 0L, 120L);
    }

    private static void processQueues(List<String> servers) {
        for (String serverName : servers) {
            Queue queue = SwiftQueueServer.getInstance().getQueueManager().getQueue(serverName.toLowerCase());
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (queue.getTotalQueue().contains(player)) {
                    User user = SwiftQueueServer.getInstance().getPlayerManager().getUser(player);
                    if (user.getPosition() == 1) {
                        handlePlayerQueue(user, player, queue);
                    }
                }
            }
        }
    }

    private static void handlePlayerQueue(User user, Player player, Queue queue) {
        user.setQueue("");
        user.setPosition(0);
        sendMessage(player, SwiftQueueServer.getInstance().getConfiguration().queueDone);
        BungeeCord.sendPlayerToServer(player, queue.getQueueServer());
        queue.removeQueue(player);
        updateOtherPlayersInQueue(queue);
    }

    private static void updateOtherPlayersInQueue(Queue queue) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            User user = SwiftQueueServer.getInstance().getPlayerManager().getUser(player);
            if (user.getQueue().equalsIgnoreCase(queue.getQueueServer())) {
                int newPosition = user.getPosition() - 1;
                user.setPosition(newPosition);
                sendMessage(player, SwiftQueueServer.getInstance().getConfiguration().queueMove
                        .replace("%position%", String.valueOf(newPosition))
                        .replace("%maxpos%", String.valueOf(queue.getTotalQueue().size())));
            }
        }
    }

    private static void sendMessage(Player player, String message) {
        player.sendMessage(SwiftQueueServer.getInstance().getConfiguration().prefix + message);
    }
}
