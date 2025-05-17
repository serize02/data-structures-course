package priority_queue;

public interface PriorityQueue<E> {

    void insert(E element, double priority);

    E removeMax() throws EmptyPriorityQueueException;

    E getMax() throws EmptyPriorityQueueException;

    boolean isEmpty();

    int size();
}
