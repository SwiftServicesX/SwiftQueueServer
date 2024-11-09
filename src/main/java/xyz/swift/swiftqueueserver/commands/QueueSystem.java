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

        String param = args[0].toLowerCase();
        if (!"on".equals(param) && !"off".equals(param)) {
            sender.sendMessage(formatMessage("&cUsage: /system <on|off>"));
            return true;
        }

        boolean enabled = "on".equals(param);
        SwiftQueueServer.getInstance().getConfiguration().system = enabled;
        sender.sendMessage(formatMessage("&7The queue system is successfully " + (enabled ? "&aonline" : "&coffline") + "&7!"));
        return true;
    }

    private String formatMessage(final String message) {
        return ChatColor.translateAlternateColorCodes('&', SwiftQueueServer.getInstance().getConfiguration().prefix + message);
    }
}
