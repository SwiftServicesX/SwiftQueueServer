package xyz.swift.swiftqueueserver;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.swift.swiftqueueserver.manager.UserManager;
import xyz.swift.swiftqueueserver.manager.queue.QueueManager;
import xyz.swift.swiftqueueserver.runnable.QueueRunnable;
import xyz.swift.swiftqueueserver.utils.Beat;
import xyz.swift.swiftqueueserver.utils.Configuration;

public final class SwiftQueueServer extends JavaPlugin {

    public static SwiftQueueServer instance;
    private UserManager userManager;
    public FileConfiguration file;
    private Configuration config;
    private QueueManager queueManager;

    @Override
    public void onEnable() {
        instance = this;
        Beat.RegisterEvents();
        this.userManager = new UserManager();
        this.file = this.getConfig();
        this.config = new xyz.swift.swiftqueueserver.utils.Configuration();
        this.queueManager = new QueueManager();
        this.config.reloadConfig();
        QueueRunnable.queueLoop(this.config.servers);
    }

    /*
    ---------------------------------------
                  Getters
    ```````````````````````````````````````
     */

    public static SwiftQueueServer getInstance() {
        return instance;
    }

    public UserManager getPlayerManager() {
        return userManager;
    }

    public Configuration getConfiguration() {
        return this.config;
    }

    public QueueManager getQueueManager() {
        return this.queueManager;
    }
}
