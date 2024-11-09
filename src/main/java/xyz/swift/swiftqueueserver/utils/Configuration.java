package xyz.swift.swiftqueueserver.utils;

import xyz.swift.swiftqueueserver.SwiftQueueServer;

import java.util.List;

public class Configuration {

    public String prefix;
    public String queueAdded;
    public String queueMove;
    public String queueDone;
    public String bypassPerm;
    public int maxQueueSize;
    public String queueFull;
    public boolean notifyPositionChange;
    public String joinQueueSound;
    public String leaveQueueSound;
    public String waitTimeMessage;
    public boolean maintenanceMode;
    public List<String> servers;
    public boolean system;

    public void reloadConfig() {
        SwiftQueueServer.getInstance().reloadConfig();
        SwiftQueueServer.getInstance().getConfig().options().copyDefaults();
        SwiftQueueServer.getInstance().saveDefaultConfig();
        reloadConfigData();
    }

    private void reloadConfigData() {
        prefix = Colorize.colorize("Prefix");

        queueAdded = Colorize.colorize("Queue_Messages.Queue_Added");
        queueMove = Colorize.colorize("Queue_Messages.Queue_Move");
        queueDone = Colorize.colorize("Queue_Messages.Queue_Done");

        bypassPerm = SwiftQueueServer.getInstance().file.getString("Bypass_Perm");
        maxQueueSize = SwiftQueueServer.getInstance().file.getInt("Max_Queue_Size");
        queueFull = Colorize.colorize("Queue_Full");
        notifyPositionChange = SwiftQueueServer.getInstance().file.getBoolean("Notify_Position_Change");
        joinQueueSound = SwiftQueueServer.getInstance().file.getString("Join_Queue_Sound");
        leaveQueueSound = SwiftQueueServer.getInstance().file.getString("Leave_Queue_Sound");
        waitTimeMessage = Colorize.colorize("Wait_Time_Message");
        maintenanceMode = SwiftQueueServer.getInstance().file.getBoolean("Maintenance_Mode");

        servers = SwiftQueueServer.getInstance().file.getStringList("Servers");
        servers.forEach(server -> {
            SwiftQueueServer.getInstance().getQueueManager().addQueue(server.toLowerCase());
            System.out.println(server.toLowerCase());
        });

        system = true; // Indicate that the system is active
    }
}
