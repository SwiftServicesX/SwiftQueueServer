package xyz.swift.swiftqueueserver.runnable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.swift.swiftqueueserver.SwiftQueueServer;
import xyz.swift.swiftqueueserver.bungee.BungeeCord;
import xyz.swift.swiftqueueserver.manager.User;
import xyz.swift.swiftqueueserver.manager.queue.Queues;

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
            Queues queues = SwiftQueueServer.getInstance().getQueueManager().getQueue(serverName.toLowerCase());
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (queues.getTotalQueue().contains(player)) {
                    User user = SwiftQueueServer.getInstance().getPlayerManager().getUser(player);
                    if (user.getPosition() == 1) {
                        handlePlayerQueue(user, player, queues);
                    }
                }
            }
        }
    }

    private static void handlePlayerQueue(User user, Player player, Queues queues) {
        user.setQueue("");
        user.setPosition(0);
        sendMessage(player, SwiftQueueServer.getInstance().getConfiguration().queueDone);
        BungeeCord.sendPlayerToServer(player, queues.getQueueServer());
        queues.removeQueue(player);
        updateOtherPlayersInQueue(queues);
    }

    private static void updateOtherPlayersInQueue(Queues queues) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            User user = SwiftQueueServer.getInstance().getPlayerManager().getUser(player);
            if (user.getQueue().equalsIgnoreCase(queues.getQueueServer())) {
                int newPosition = user.getPosition() - 1;
                user.setPosition(newPosition);
                sendMessage(player, SwiftQueueServer.getInstance().getConfiguration().queueMove
                        .replace("%position%", String.valueOf(newPosition))
                        .replace("%maxpos%", String.valueOf(queues.getTotalQueue().size())));
            }
        }
    }

    private static void sendMessage(Player player, String message) {
        player.sendMessage(SwiftQueueServer.getInstance().getConfiguration().prefix + message);
    }
}
