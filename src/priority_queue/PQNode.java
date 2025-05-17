package priority_queue;

public class PQNode<E> {

    private E element;
    private double priority;

    public PQNode(E element, double priority) {
        this.element = element;
        this.priority = priority;
    }

    public E getElement() {
        return element;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }
}

