package xyz.swift.swiftqueueserver.manager;

import java.util.UUID;

public class User {
    private final String name;
    private final UUID uuid;
    private int position;
    private String queue;

    public User(final UUID uuid, final String name) {
        this.uuid = uuid;
        this.name = name;
        this.position = 0;
        this.queue = "";
    }

    public String getName() {
        return this.name;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public int getPosition() {
        return this.position;
    }

    public String getQueue() {
        return this.queue;
    }

    public void setPosition(final int position) {
        this.position = position;
    }

    public void setQueue(final String queue) {
        this.queue = queue;
    }
}
