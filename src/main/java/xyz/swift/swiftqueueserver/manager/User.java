package xyz.swift.swiftqueueserver.manager;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class User {
    @Getter
    private final String name;
    private final UUID uuid;
    @Setter
    @Getter
    private int position;
    @Setter
    @Getter
    private String queue;

    public User(final UUID uuid, final String name) {
        this.uuid = uuid;
        this.name = name;
        this.position = 0;
        this.queue = "";
    }

    public UUID getUUID() {
        return this.uuid;
    }

}
