package graphs;

import java.util.LinkedList;

public interface Graph {

    public int addNode(String name);

    void addEdge(String source, String destination, float cost);

    void addEdge(String source, String destination);

    void removeEdge(String source, String destination) throws Exception;

    void removeNode(String name) throws Exception;

    boolean isAdjacent(String node1, String node2);

    LinkedList<String> bfs(String startNode);

    LinkedList<String> dfs(String startNode);

    void shortestPathUnweighted(String startNode) throws Exception;

    boolean shortestPathPositiveWeights(String startNode);

    boolean shortestPathNegativeWeights(String startNode);

    LinkedList<String> topologicalSort();

    boolean shortestPathAcyclic(String startNode);
}
