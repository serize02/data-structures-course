package searchengine;
import priority_queue.EmptyPriorityQueueException;
import priority_queue.PriorityQueueHeap;
import stmatching.StringMatching;

public class Engine {

    private String [] documents;
    private String pattern;
    private StringMatching matcher;
    private PriorityQueueHeap queue;

    public Engine(String[] documents, String pattern) {
        this.documents = documents;
        this.pattern = pattern;
        this.matcher = new StringMatching();
        this.queue = new PriorityQueueHeap();

        for (String document : documents) {
            int frequency = matcher.boyerMoore(document, pattern);
            queue.insert(document, frequency);
        }
    }

    public double bruteForce() {
        long start = System.nanoTime();
        for (String document : documents) {
            matcher.bruteForce(document, pattern);
        }
        long end = System.nanoTime();
        double elapsedMillis = (end - start) / 1_000_000.0;
        return elapsedMillis;
    }

    public double boyerMoore() {
        long start = System.nanoTime();
        for (String document : documents) {
            matcher.boyerMoore(document, pattern);
        }
        long end = System.nanoTime();
        double elapsedMillis = (end - start) / 1_000_000.0;
        return elapsedMillis;
    }

    public void compareMethods() {
        System.out.println("BruteForce: " + bruteForce());
        System.out.println("BoyerMoore: " + boyerMoore());
    }

    public void priority() throws EmptyPriorityQueueException {
        PriorityQueueHeap heap = queue;
        while (!heap.isEmpty()) {
            System.out.println(heap.removeMax());
        }
    }
}
