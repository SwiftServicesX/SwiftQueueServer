package xyz.swift.swiftqueueserver.manager.queue;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class QueueManager {
    private final Set<Queue> queues;

    public QueueManager() {
        this.queues = new HashSet<>();
    }

    public void addQueue(final String queue) {
        this.queues.add(new Queue(queue));
    }

    public void removeQueue(final String queue) {
        this.queues.remove(queue);
    }

    public Queue getQueue(final String queue) {
        return this.queues.stream().filter(queues -> queues.getQueueServer().equals(queue)).findAny().orElse(null);
    }
}
