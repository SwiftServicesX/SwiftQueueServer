package xyz.swift.swiftqueueserver.manager.queue;

import java.util.HashSet;
import java.util.Set;

public class QueueManager {
    private final Set<Queues> queues;

    public QueueManager() {
        this.queues = new HashSet<>();
    }

    public void addQueue(final String queue) {
        this.queues.add(new Queues(queue));
    }

    public void removeQueue(final String queue) {
        this.queues.remove(queue);
    }

    public Set<Queues> getQueues() {
        return this.queues;
    }

    public Queues getQueue(final String queue) {
        return this.queues.stream().filter(queues -> queues.getQueueServer().equals(queue)).findAny().orElse(null);
    }
}
