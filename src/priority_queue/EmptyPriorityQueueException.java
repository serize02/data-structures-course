package priority_queue;

public class EmptyPriorityQueueException extends Exception {
    public EmptyPriorityQueueException() {
        super("Priority queue is empty.");
    }
}
