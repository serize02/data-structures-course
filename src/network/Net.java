package network;

import graphs.AdjacencyListGraph;
import priority_queue.EmptyPriorityQueueException;
import priority_queue.PriorityQueueHeap;
import stmatching.StringMatching;

import java.util.ArrayList;
import java.util.List;

public class Net {

    private AdjacencyListGraph graph;
    private StringMatching matcher;
    private List<String> names;

    public Net() {
        this.graph = new AdjacencyListGraph();
        this.matcher = new StringMatching();
        this.names = new ArrayList<>();
    }

    public void addUser(String name) {
        names.add(name);
        graph.addNode(name);
    }

    public void addFriend(String name1, String name2) {
        graph.addEdge(name1, name2);
        graph.addEdge(name2, name1);
    }

    public void KFriends(String name, int k) {
        System.out.println(graph.KBFS(name, k));
    }

    public void recommendation(String name) throws EmptyPriorityQueueException {

        PriorityQueueHeap<String> heap = new PriorityQueueHeap<>();

        for (int i = 0; i < names.size(); i++) {
            int nodes = graph.countCommon(name, names.get(i));
            if (nodes == -1) continue;
            heap.insert(names.get(i), nodes);
        }

        List<String> res = new ArrayList<>();
        while (!heap.isEmpty()) res.add(heap.removeMax());

        System.out.println(res);
    }

    public void find(String name) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            if (matcher.boyerMoore(names.get(i), name) != -1) {
                res.add(names.get(i));
            }
        }
        System.out.println(res);
    }
}
