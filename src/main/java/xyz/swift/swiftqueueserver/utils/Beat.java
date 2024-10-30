package xyz.swift.swiftqueueserver.utils;

import xyz.swift.swiftqueueserver.SwiftQueueServer;
import xyz.swift.swiftqueueserver.commands.QueueCommand;
import xyz.swift.swiftqueueserver.commands.QueueSystem;
import xyz.swift.swiftqueueserver.listeners.JoinListener;
import xyz.swift.swiftqueueserver.listeners.QuitListener;

public class Beat {

    public static void RegisterEvents() {
        final SwiftQueueServer plugin = SwiftQueueServer.getInstance();

        // Register commands
        plugin.getCommand("queue").setExecutor(new QueueCommand());
        plugin.getCommand("system").setExecutor(new QueueSystem());

        // Register event listeners
        plugin.getServer().getPluginManager().registerEvents(new QuitListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new JoinListener(), plugin);

        // Register the outgoing plugin channel for BungeeCord communication
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
    }
}
