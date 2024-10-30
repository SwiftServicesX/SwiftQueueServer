package xyz.swift.swiftqueueserver.bungee;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import xyz.swift.swiftqueueserver.SwiftQueueServer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeCord {

    public static void sendPlayerToServer(Player player, String server) {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream();
             DataOutputStream out = new DataOutputStream(b)) {

            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(SwiftQueueServer.getInstance(), "BungeeCord", b.toByteArray());
        } catch (IOException e) {
            player.sendMessage(ChatColor.RED + "Error when trying to connect to " + server + ": " + e.getMessage());
        }
    }
}
