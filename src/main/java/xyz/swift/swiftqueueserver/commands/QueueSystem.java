package xyz.swift.swiftqueueserver.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.swift.swiftqueueserver.SwiftQueueServer;

public class QueueSystem implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length == 0) {
            sender.sendMessage(formatMessage("&cUsage: /system <on|off>"));
            return true;
        }

        if (args[0].equalsIgnoreCase("on")) {
            turnOnQueueSystem(sender);
        } else if (args[0].equalsIgnoreCase("off")) {
            turnOffQueueSystem(sender);
        } else {
            sender.sendMessage(formatMessage("&cUsage: /system <on|off>"));
        }
        return true;
    }

    private void turnOnQueueSystem(final CommandSender sender) {
        SwiftQueueServer.getInstance().getConfiguration().system = true;
        sender.sendMessage(formatMessage("&7The queue system is successfully &aonline&7!"));
    }

    private void turnOffQueueSystem(final CommandSender sender) {
        SwiftQueueServer.getInstance().getConfiguration().system = false;
        sender.sendMessage(formatMessage("&7The queue system is successfully &coffline&7!"));
    }

    private String formatMessage(final String message) {
        return ChatColor.translateAlternateColorCodes('&', SwiftQueueServer.getInstance().getConfiguration().prefix + message);
    }
}
