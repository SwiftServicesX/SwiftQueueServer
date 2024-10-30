package xyz.swift.swiftqueueserver.utils;

import net.md_5.bungee.api.ChatColor;
import xyz.swift.swiftqueueserver.SwiftQueueServer;

import java.util.List;

public class Configuration {

    public String prefix;
    public String queueAdded;
    public String queueMove;
    public String queueDone;
    public String bypassPerm;
    public int Max_Queue_Size;
    public String Queue_Full;
    public boolean Notify_Position_Change;
    public String Join_Queue_Sound;
    public String Leave_Queue_Sound;
    public String Wait_Time_Message;
    public boolean Maintenance_Mode;
    public List<String> servers;
    public boolean system;

    public void reloadConfig() {
        SwiftQueueServer.getInstance().reloadConfig();
        SwiftQueueServer.getInstance().getConfig().options().copyDefaults();
        SwiftQueueServer.getInstance().saveDefaultConfig();
        reloadConfigData();
    }

    private void reloadConfigData() {
        prefix = translateConfigValue("Prefix");

        queueAdded = translateConfigValue("Queue_Messages.Queue_Added");
        queueMove = translateConfigValue("Queue_Messages.Queue_Move");
        queueDone = translateConfigValue("Queue_Messages.Queue_Done");

        bypassPerm = SwiftQueueServer.getInstance().file.getString("Bypass_Perm");
        Max_Queue_Size = SwiftQueueServer.getInstance().file.getInt("Max_Queue_Size");
        Queue_Full = translateConfigValue("Queue_Full");
        Notify_Position_Change = SwiftQueueServer.getInstance().file.getBoolean("Notify_Position_Change");
        Join_Queue_Sound = SwiftQueueServer.getInstance().file.getString("Join_Queue_Sound");
        Leave_Queue_Sound = SwiftQueueServer.getInstance().file.getString("Leave_Queue_Sound");
        Wait_Time_Message = translateConfigValue("Wait_Time_Message");
        Maintenance_Mode = SwiftQueueServer.getInstance().file.getBoolean("Maintenance_Mode");

        servers = SwiftQueueServer.getInstance().file.getStringList("Servers");
        servers.forEach(server -> {
            SwiftQueueServer.getInstance().getQueueManager().addQueue(server.toLowerCase());
            System.out.println(server.toLowerCase());
        });

        system = true; // Indicate that the system is active
    }

    private String translateConfigValue(final String key) {
        return ChatColor.translateAlternateColorCodes('&', SwiftQueueServer.getInstance().file.getString(key));
    }
}
