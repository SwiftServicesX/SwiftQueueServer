package xyz.swift.swiftqueueserver;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.swift.swiftqueueserver.commands.QueueCommand;
import xyz.swift.swiftqueueserver.commands.QueueSystem;
import xyz.swift.swiftqueueserver.listeners.JoinListener;
import xyz.swift.swiftqueueserver.listeners.QuitListener;
import xyz.swift.swiftqueueserver.manager.UserManager;
import xyz.swift.swiftqueueserver.manager.queue.QueueManager;
import xyz.swift.swiftqueueserver.runnable.QueueRunnable;
import xyz.swift.swiftqueueserver.utils.Configuration;

public final class SwiftQueueServer extends JavaPlugin {

    @Getter
    public static SwiftQueueServer instance;
    private UserManager userManager;
    public FileConfiguration file;
    private Configuration config;
    @Getter
    private QueueManager queueManager;

    @Override
    public void onEnable() {
        instance = this;
        // Register Commands
        getCommand("queue").setExecutor(new QueueCommand());
        getCommand("system").setExecutor(new QueueSystem());

        // Register event listeners
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        // Register the outgoing plugin channel for BungeeCord communication
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        this.userManager = new UserManager();
        this.file = this.getConfig();
        this.config = new xyz.swift.swiftqueueserver.utils.Configuration();
        this.queueManager = new QueueManager();
        QueueRunnable.queueLoop(this.config.servers);
    }

    /*
    ---------------------------------------
                  Getters
    ```````````````````````````````````````
     */

    public UserManager getPlayerManager() {
        return userManager;
    }

    public Configuration getConfiguration() {
        return this.config;
    }

}
