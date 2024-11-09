package xyz.swift.swiftqueueserver.utils;

import net.md_5.bungee.api.ChatColor;
import xyz.swift.swiftqueueserver.SwiftQueueServer;

public class Colorize {

    public static String colorize(final String key) {
        return ChatColor.translateAlternateColorCodes('&', SwiftQueueServer.getInstance().file.getString(key));
    }

    public static String formatMessage(final String message) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', SwiftQueueServer.getInstance().getConfiguration().prefix + message);
    }
}
