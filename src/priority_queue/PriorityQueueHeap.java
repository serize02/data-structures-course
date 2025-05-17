package priority_queue;

@SuppressWarnings("unchecked")
public class PriorityQueueHeap<E> implements PriorityQueue<E> {
    private PQNode<E>[] nodes = new PQNode[10];
    private int capacity = 10;
    private int numNodes = 0;

    @Override
    public void insert(E element, double priority) {
        PQNode<E> newNode = new PQNode<>(element, priority);

        if (numNodes == capacity) {
            PQNode<E>[] newArray = new PQNode[capacity * 2];
            System.arraycopy(nodes, 0, newArray, 0, capacity);
            nodes = newArray;
            capacity *= 2;
        }

        nodes[numNodes] = newNode;
        heapifyUp(numNodes);
        numNodes++;
    }

    @Override
    public E removeMax() throws EmptyPriorityQueueException {
        if (isEmpty()) throw new EmptyPriorityQueueException();

        E maxElement = nodes[0].getElement();
        nodes[0] = nodes[numNodes - 1];
        nodes[numNodes - 1] = null;
        numNodes--;

        heapifyDown(0);
        return maxElement;
    }

    @Override
    public E getMax() throws EmptyPriorityQueueException {
        if (isEmpty()) throw new EmptyPriorityQueueException();
        return nodes[0].getElement();
    }

    @Override
    public boolean isEmpty() {
        return numNodes == 0;
    }

    @Override
    public int size() {
        return numNodes;
    }

    public void changePriority(E element, double newPriority) throws ElementNotFoundException {
        int pos = -1;
        for (int i = 0; i < numNodes; i++) {
            if (nodes[i].getElement().equals(element)) {
                pos = i;
                break;
            }
        }

        if (pos == -1) throw new ElementNotFoundException();

        double oldPriority = nodes[pos].getPriority();
        nodes[pos].setPriority(newPriority);

        if (newPriority > oldPriority) {
            heapifyUp(pos);
        } else {
            heapifyDown(pos);
        }
    }

    public PriorityQueue<E> filterbyPriority(double min, double max) {
        PriorityQueueHeap<E> filteredQueue = new PriorityQueueHeap<>();
        for (int i = 0; i < numNodes; i++) {
            if (nodes[i].getPriority() >= min && nodes[i].getPriority() <= max) {
                filteredQueue.insert(nodes[i].getElement(), nodes[i].getPriority());
            }
        }
        return filteredQueue;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (nodes[index].getPriority() > nodes[parent].getPriority()) {
                swap(index, parent);
                index = parent;
            } else break;
        }
    }

    private void heapifyDown(int index) {
        while (index < numNodes) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int largest = index;

            if (left < numNodes && nodes[left].getPriority() > nodes[largest].getPriority()) {
                largest = left;
            }

            if (right < numNodes && nodes[right].getPriority() > nodes[largest].getPriority()) {
                largest = right;
            }

            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else break;
        }
    }

    private void swap(int i, int j) {
        PQNode<E> temp = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = temp;
    }
}

