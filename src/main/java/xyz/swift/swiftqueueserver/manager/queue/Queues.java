package xyz.swift.swiftqueueserver.manager.queue;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Queues {

    private final String queueServer;
    private final ArrayList<Player> totalQueue;

    public Queues(final String queue) {
        this.queueServer = queue;
        this.totalQueue = new ArrayList<>();
    }

    public void removeQueue(final Player player) {
        this.totalQueue.remove(player);
    }

    public void addQueue(final Player player) {
        this.totalQueue.add(player);
    }

    public String getQueueServer() {
        return this.queueServer;
    }

    public ArrayList<Player> getTotalQueue() {
        return this.totalQueue;
    }
}
