package xyz.swift.swiftqueueserver.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.swift.swiftqueueserver.SwiftQueueServer;
import xyz.swift.swiftqueueserver.bungee.BungeeCord;
import xyz.swift.swiftqueueserver.manager.User;
import xyz.swift.swiftqueueserver.manager.queue.QueueManager;
import xyz.swift.swiftqueueserver.manager.queue.Queue;
import xyz.swift.swiftqueueserver.utils.Colorize;

public class QueueCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Colorize.formatMessage("&cYou must be a player to use this command."));
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
            player.sendMessage(Colorize.formatMessage("&cThe queue is currently paused; please try again later."));
            return;
        }

        // Check if the server is in maintenance mode
        if (SwiftQueueServer.getInstance().getConfiguration().maintenanceMode) {
            player.sendMessage(Colorize.formatMessage("&cThe server is currently in maintenance mode; please try again later."));
            return;
        }

        if (server == null) {
            player.sendMessage(Colorize.formatMessage("&cUsage: /queue join <server>"));
            return;
        }

        final User user = SwiftQueueServer.getInstance().getPlayerManager().getUser(player);

        // Check if the player is already in a queue
        if (user.getPosition() != 0) {
            player.sendMessage(Colorize.formatMessage("&cYou are already in a queue."));
            return;
        }

        final QueueManager queueManager = SwiftQueueServer.getInstance().getQueueManager();
        final Queue queue = queueManager.getQueue(server.toLowerCase());

        // Check if the server has a queue
        if (queue == null) {
            player.sendMessage(Colorize.formatMessage("&cThis server is not in a queue."));
            return;
        }

        if (player.hasPermission(SwiftQueueServer.getInstance().getConfiguration().bypassPerm)) {
            player.sendMessage(Colorize.formatMessage(SwiftQueueServer.getInstance().getConfiguration().queueDone));
            BungeeCord.sendPlayerToServer(player, server);
        } else {
            // Add to the queue
            queue.addQueue(player);
            user.setQueue(server);
            final int queueSize = queue.getTotalQueue().size();
            user.setPosition(queueSize);

            // Notify the player of their position in the queue
            player.sendMessage(Colorize.formatMessage(
                    SwiftQueueServer.getInstance().getConfiguration().queueAdded
                            .replace("%position%", String.valueOf(queueSize))
                            .replace("%maxpos%", String.valueOf(queue.getTotalQueue().size()))
            ));

            // Play join sound
            player.playSound(player.getLocation(), SwiftQueueServer.getInstance().getConfiguration().joinQueueSound, 1.0f, 1.0f);
        }
    }

    private void onLeaveQueue(final Player player) {
        final User user = SwiftQueueServer.getInstance().getPlayerManager().getUser(player);

        // Check if the user is actually in a queue
        if (user.getPosition() == 0) {
            player.sendMessage(Colorize.formatMessage("&cYou are not in a queue."));
            return;
        }

        final Queue queue = SwiftQueueServer.getInstance().getQueueManager().getQueue(user.getQueue().toLowerCase());

        if (queue != null) {
            queue.removeQueue(player);
            user.setPosition(0); // Reset position
            player.sendMessage(Colorize.formatMessage(SwiftQueueServer.getInstance().getConfiguration().queueDone));

            // Play leave sound
            player.playSound(player.getLocation(), SwiftQueueServer.getInstance().getConfiguration().leaveQueueSound, 1.0f, 1.0f);
        } else {
            player.sendMessage(Colorize.formatMessage("&cAn error occurred while trying to leave the queue."));
        }
    }

    private void onNoArguments(final Player player) {
        player.sendMessage(Colorize.formatMessage("&cUsage: /queue <join|leave> [server]"));
    }
}
