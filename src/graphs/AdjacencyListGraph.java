package graphs;
import java.util.*;

public class AdjacencyListGraph implements Graph {
    private static final int INITIAL_TABLE_SIZE = 10;
    private int nodeCount;
    private ExtendedNode[] table;
    private final float INFINITY = Float.MAX_VALUE;

    public AdjacencyListGraph() {
        nodeCount = 0;
        table = new ExtendedNode[INITIAL_TABLE_SIZE];
    }

    // -- helpers --
    private int find(String name) {
        for (int i = 0; i < nodeCount; i++) {
            if (table[i].getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private void expandTable() {
        ExtendedNode[] newTable = new ExtendedNode[table.length * 2];
        System.arraycopy(table, 0, newTable, 0, table.length);
        table = newTable;
    }

    private void reset() {
        for (int i = 0; i < nodeCount; i++) {
            table[i].setDistance(INFINITY);
            table[i].setPredecessor(-1);
            table[i].setExtra(0);
        }
    }

    public Float getDistance(String node) {
        int pos = find(node);
        if (pos == -1) {
            return null;
        }
        return table[pos].getDistance();
    }

    public List<Float> getDistances() {
        List<Float> distances = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            distances.add(table[i].getDistance());
        }
        return distances;
    }

    public String getPath(String node) {
        int pos = find(node);
        List<String> path = new ArrayList<>();
        while (pos != -1) {
            path.add(table[pos].getName());
            pos = table[pos].getPredecessor();
        }
        Collections.reverse(path);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            builder.append(path.get(i));
            if (i != path.size() - 1) {
                builder.append(" -> ");
            }
        }
        return builder.toString();
    }

    // -- interface-methods--
    @Override
    public int addNode(String name) {
        for (int i = 0; i < nodeCount; i++) {
            if (table[i].getName().equals(name)) {
                return i;
            }
        }
        ExtendedNode newNode = new ExtendedNode(name);
        if (table.length == nodeCount) expandTable();
        table[nodeCount] = newNode;
        return nodeCount++;
    }

    @Override
    public void addEdge(String source, String destination, float cost) {
        int sourceIndex = addNode(source);
        int destIndex = addNode(destination);
        Edge newEdge = new Edge(destIndex, cost);
        table[sourceIndex].getAdjacencyList().add(newEdge);
    }

    @Override
    public void addEdge(String source, String destination) {
        addEdge(source, destination, 0);
    }

    @Override
    public void removeEdge(String source, String destination) {
        int srcIndex = find(source);
        int destIndex = find(destination);

        if (srcIndex != -1 && destIndex != -1) {
            Iterator<Edge> it = table[srcIndex].getAdjacencyList().iterator();
            while (it.hasNext()) {
                Edge edge = it.next();
                if (edge.getDestination() == destIndex) {
                    it.remove();
                    return;
                }
            }
        }
        System.out.println("Edge does not exist");
    }

    @Override
    public void removeNode(String name) {
        int index = find(name);
        if (index == -1) {
            System.out.println("Node does not exist");
            return;
        }

        table[index] = table[--nodeCount];

        for (int i = 0; i < nodeCount; i++) {
            Iterator<Edge> it = table[i].getAdjacencyList().iterator();
            while (it.hasNext()) {
                Edge edge = it.next();
                if (edge.getDestination() == nodeCount) {
                    edge.setDestination(index);
                } else if (edge.getDestination() == index) {
                    it.remove();
                }
            }
        }
    }

    @Override
    public boolean isAdjacent(String vertex1, String vertex2) {
        int i = find(vertex1);
        int j = find(vertex2);

        if (i != -1 && j != -1) {
            for (Edge edge : table[i].getAdjacencyList()) {
                if (edge.getDestination() == j) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public LinkedList<String> bfs(String startNode) {
        reset();
        LinkedList<String> result = new LinkedList<>();
        Queue<Integer> queue = new LinkedList<>();

        int startIndex = find(startNode);
        if (startIndex == -1) return result;

        boolean repeat = true;
        while (repeat) {
            table[startIndex].setExtra(1);
            result.add(table[startIndex].getName());
            queue.add(startIndex);

            while (!queue.isEmpty()) {
                int current = queue.poll();
                for (Edge edge : table[current].getAdjacencyList()) {
                    int neighbor = edge.getDestination();
                    if (table[neighbor].getExtra() == 0) {
                        table[neighbor].setExtra(1);
                        result.add(table[neighbor].getName());
                        queue.add(neighbor);
                    }
                }
            }

            repeat = false;
            for (int i = 0; i < nodeCount; i++) {
                if (table[i].getExtra() == 0) {
                    startIndex = i;
                    repeat = true;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public LinkedList<String> dfs(String startNode) {
        reset();
        LinkedList<String> result = new LinkedList<>();
        int startIndex = find(startNode);

        if (startIndex != -1) {
            dfsRecursive(startIndex, result);

            // Para vértices no conectados
            for (int i = 0; i < nodeCount; i++) {
                if (table[i].getExtra() == 0) {
                    dfsRecursive(i, result);
                }
            }
        }
        return result;
    }

    private void dfsRecursive(int vertexIndex, LinkedList<String> result) {
        table[vertexIndex].setExtra(1);
        result.add(table[vertexIndex].getName());

        for (Edge edge : table[vertexIndex].getAdjacencyList()) {
            int neighbor = edge.getDestination();
            if (table[neighbor].getExtra() == 0) {
                dfsRecursive(neighbor, result);
            }
        }
    }

    @Override
    public void shortestPathUnweighted(String startNode) {
        reset();
        int startIndex = find(startNode);
        if (startIndex == -1) return;

        Queue<Integer> queue = new LinkedList<>();
        table[startIndex].setDistance(0);
        queue.add(startIndex);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (Edge edge : table[current].getAdjacencyList()) {
                int neighbor = edge.getDestination();
                if (table[neighbor].getDistance() == INFINITY) {
                    table[neighbor].setDistance(table[current].getDistance() + 1);
                    table[neighbor].setPredecessor(current);
                    queue.add(neighbor);
                }
            }
        }
    }

    @Override
    public boolean shortestPathPositiveWeights(String startNode) {
        reset();
        int startIndex = find(startNode);
        if (startIndex == -1) return false;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        table[startIndex].setDistance(0);
        pq.add(new Node(table[startIndex].getName(), 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = find(current.getName());

            if (table[u].getExtra() == 1) continue;
            table[u].setExtra(1);

            for (Edge edge : table[u].getAdjacencyList()) {
                int v = edge.getDestination();
                float newDist = table[u].getDistance() + edge.getCost();

                if (edge.getCost() < 0) {
                    return false; // Pesos negativos no permitidos
                }

                if (newDist < table[v].getDistance()) {
                    table[v].setDistance(newDist);
                    table[v].setPredecessor(u);
                    pq.add(new Node(table[v].getName(), newDist));
                }
            }
        }
        return true;
    }

    @Override
    public boolean shortestPathNegativeWeights(String startNode) {
        int startIndex = find(startNode);
        if (startIndex == -1) return false;

        reset();
        table[startIndex].setDistance(0);

        for (int i = 1; i < nodeCount; i++) {
            for (int u = 0; u < nodeCount; u++) {
                for (Edge edge : table[u].getAdjacencyList()) {
                    int v = edge.getDestination();
                    float newDist = table[u].getDistance() + edge.getCost();
                    if (newDist < table[v].getDistance()) {
                        table[v].setDistance(newDist);
                        table[v].setPredecessor(u);
                    }
                }
            }
        }

        for (int u = 0; u < nodeCount; u++) {
            for (Edge edge : table[u].getAdjacencyList()) {
                int v = edge.getDestination();
                if (table[u].getDistance() + edge.getCost() < table[v].getDistance()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public LinkedList<String> topologicalSort() {
        LinkedList<String> result = new LinkedList<>();
        int[] inDegree = new int[nodeCount];
        Queue<Integer> queue = new LinkedList<>();

        for (int u = 0; u < nodeCount; u++) {
            for (Edge edge : table[u].getAdjacencyList()) {
                inDegree[edge.getDestination()]++;
            }
        }

        for (int i = 0; i < nodeCount; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        int count = 0;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            result.add(table[u].getName());
            count++;

            for (Edge edge : table[u].getAdjacencyList()) {
                int v = edge.getDestination();
                if (--inDegree[v] == 0) {
                    queue.add(v);
                }
            }
        }

        if (count != nodeCount) return null;
        return result;
    }

    @Override
    public boolean shortestPathAcyclic(String startNode) {
        LinkedList<String> topOrder = topologicalSort();
        if (topOrder == null) return false;
        reset();
        int startIndex = find(startNode);
        if (startIndex == -1) return false;

        table[startIndex].setDistance(0);

        for (String nodeName : topOrder) {
            int u = find(nodeName);
            if (table[u].getDistance() != INFINITY) {
                for (Edge edge : table[u].getAdjacencyList()) {
                    int v = edge.getDestination();
                    float newDist = table[u].getDistance() + edge.getCost();
                    if (newDist < table[v].getDistance()) {
                        table[v].setDistance(newDist);
                        table[v].setPredecessor(u);
                    }
                }
            }
        }
        return true;
    }
}