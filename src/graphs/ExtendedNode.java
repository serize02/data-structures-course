package graphs;

import java.util.LinkedList;

public class ExtendedNode extends Node {

    private LinkedList<Edge> adjacencyList;

    public ExtendedNode(String name) {
        super(name);
        adjacencyList = new LinkedList<Edge>();
    }

    public LinkedList<Edge> getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(LinkedList<Edge> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }
}
