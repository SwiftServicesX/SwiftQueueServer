package xyz.swift.swiftqueueserver.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.swift.swiftqueueserver.SwiftQueueServer;
import xyz.swift.swiftqueueserver.bungee.BungeeCord;
import xyz.swift.swiftqueueserver.manager.User;
import xyz.swift.swiftqueueserver.manager.queue.QueueManager;
import xyz.swift.swiftqueueserver.manager.queue.Queues;

public class QueueCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(formatMessage("&cYou must be a player to use this command."));
            return true;
        }

        final Player player = (Player) sender;

        if (args.length == 0) {
            onNoArguments(player);
        } else if (args[0].equalsIgnoreCase("join")) {
            onJoinQueue(player, args.length > 1 ? args[1] : null);
        } else if (args[0].equalsIgnoreCase("leave")) {
            onLeaveQueue(player);
        } else {
            onNoArguments(player);
        }
        return true;
    }

    private void onJoinQueue(final Player player, final String server) {
        // Check if the queue system is active
        if (!SwiftQueueServer.getInstance().getConfiguration().system) {
            player.sendMessage(formatMessage("&cThe queue is currently paused; please try again later."));
            return;
        }

        // Check if the server is in maintenance mode
        if (SwiftQueueServer.getInstance().getConfiguration().Maintenance_Mode) {
            player.sendMessage(formatMessage("&cThe server is currently in maintenance mode; please try again later."));
            return;
        }

        if (server == null) {
            player.sendMessage(formatMessage("&cUsage: /queue join <server>"));
            return;
        }

        final User user = SwiftQueueServer.getInstance().getPlayerManager().getUser(player);

        // Check if the player is already in a queue
        if (user.getPosition() != 0) {
            player.sendMessage(formatMessage("&cYou are already in a queue."));
            return;
        }

        final QueueManager queueManager = SwiftQueueServer.getInstance().getQueueManager();
        final Queues queues = queueManager.getQueue(server.toLowerCase());

        // Check if the server has a queue
        if (queues == null) {
            player.sendMessage(formatMessage("&cThis server is not in a queue."));
            return;
        }

        if (player.hasPermission(SwiftQueueServer.getInstance().getConfiguration().bypassPerm)) {
            player.sendMessage(formatMessage(SwiftQueueServer.getInstance().getConfiguration().queueDone));
            BungeeCord.sendPlayerToServer(player, server);
        } else {
            // Add to the queue
            queues.addQueue(player);
            user.setQueue(server);
            final int queueSize = queues.getTotalQueue().size();
            user.setPosition(queueSize);

            // Notify the player of their position in the queue
            player.sendMessage(formatMessage(
                    SwiftQueueServer.getInstance().getConfiguration().queueAdded
                            .replace("%position%", String.valueOf(queueSize))
                            .replace("%maxpos%", String.valueOf(queues.getTotalQueue().size()))
            ));

            // Play join sound
            player.playSound(player.getLocation(), SwiftQueueServer.getInstance().getConfiguration().Join_Queue_Sound, 1.0f, 1.0f);
        }
    }

    private void onLeaveQueue(final Player player) {
        final User user = SwiftQueueServer.getInstance().getPlayerManager().getUser(player);

        // Check if the user is actually in a queue
        if (user.getPosition() == 0) {
            player.sendMessage(formatMessage("&cYou are not in a queue."));
            return;
        }

        final Queues queues = SwiftQueueServer.getInstance().getQueueManager().getQueue(user.getQueue().toLowerCase());

        if (queues != null) {
            queues.removeQueue(player);
            user.setPosition(0); // Reset position
            player.sendMessage(formatMessage(SwiftQueueServer.getInstance().getConfiguration().queueDone));

            // Play leave sound
            player.playSound(player.getLocation(), SwiftQueueServer.getInstance().getConfiguration().Leave_Queue_Sound, 1.0f, 1.0f);
        } else {
            player.sendMessage(formatMessage("&cAn error occurred while trying to leave the queue."));
        }
    }

    private void onNoArguments(final Player player) {
        player.sendMessage(formatMessage("&cUsage: /queue <join|leave> [server]"));
    }

    private String formatMessage(final String message) {
        return ChatColor.translateAlternateColorCodes('&', SwiftQueueServer.getInstance().getConfiguration().prefix + message);
    }
}
