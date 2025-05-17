package graphs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class AdjacencyMatrixGraph implements Graph {

    private static final int INITIAL_TABLE_SIZE = 10;
    private int nodeCount;
    private float[][] matrix;
    private Node[] table;
    private final float INF = Float.MAX_VALUE;

    public AdjacencyMatrixGraph() {
        nodeCount = 0;
        matrix = new float[INITIAL_TABLE_SIZE][INITIAL_TABLE_SIZE];
        table = new Node[INITIAL_TABLE_SIZE];
    }

    // -- helpers --
    private void reset() {
        for (int i = 0; i < nodeCount; i++) {
            table[i].setExtra(0);
            table[i].setPredecessor(-1);
            table[i].setDistance(INF);
        }
    }

    private void expandTable() {
        Node[] newTable = new Node[table.length * 2];
        System.arraycopy(table, 0, newTable, 0, table.length);
        table = newTable;

        float[][] newMatrix = new float[matrix.length * 2][matrix.length * 2];
        for (int j = 0; j < matrix.length; j++) {
            System.arraycopy(matrix[j], 0, newMatrix[j], 0, matrix.length);
        }
        matrix = newMatrix;
    }

    private int find(String name) {
        for (int i = 0; i < nodeCount; i++) {
            if (table[i].getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    // -- interface methods --
    @Override
    public int addNode(String name) {
        int index = find(name);
        if (index == -1) return index;
        Node node = new Node(name);
        if (table.length == nodeCount) expandTable();
        table[nodeCount] = node;
        return nodeCount++;
    }


    @Override
    public void addEdge(String source, String destination, float cost) {
        int sourceIndex = addNode(source);
        int destinationIndex = addNode(destination);
        matrix[sourceIndex][destinationIndex] = cost;
    }

    @Override
    public void addEdge(String source, String destination) {
        addEdge(source, destination, 1);
    }

    @Override
    public void removeEdge(String source, String destination) throws Exception {
        int i = find(source);
        int j = find(destination);
        if (i != -1 && j != -1) {
            matrix[i][j] = 0;
        }
        else throw new Exception("The edge does not exists");
    }

    @Override
    public void removeNode(String name) throws Exception {
        int i = find(name);
        if (i != -1) {
            if (i == nodeCount - 1) nodeCount--;
            else {
                table[i] = table[nodeCount - 1];
                nodeCount--;
                System.arraycopy(matrix[nodeCount], 0, matrix[i], 0, nodeCount + 1);
                for (int k = 0; k <= nodeCount; k++) {
                    matrix[k][i] = matrix[k][nodeCount];
                }
            }
            Arrays.fill(matrix[nodeCount], 0);
            for (int l = 0; l <= nodeCount; l++) {
                matrix[l][nodeCount] = 0;
            }
        } else throw new Exception("The node does not exist");
    }

    @Override
    public boolean isAdjacent(String node1, String node2) {
        int i = find(node1);
        int j = find(node2);
        return i != -1 && j != -1 && matrix[i][j] != 0;
    }

    @Override
    public LinkedList<String> bfs(String startNode) {

        reset();
        int startIndex = addNode(startNode);
        Queue<Integer> queue = new LinkedList<>();
        LinkedList<String> result = new LinkedList<>();

        table[startIndex].setExtra(1);
        result.add(table[startIndex].getName());
        queue.add(startIndex);

        while (!queue.isEmpty()) {
            int current = queue.remove();
            for (int i = 0; i < nodeCount; i++) {
                if (matrix[current][i] != 0 && table[i].getExtra() != 1) {
                    result.add(table[i].getName());
                    table[i].setExtra(1);
                    queue.add(i);
                }
            }
        }

        return result;
    }

    @Override
    public LinkedList<String> dfs(String startVertex) {
        reset();
        LinkedList<String> result = new LinkedList<>();
        int startIndex = find(startVertex);
        if (startIndex != -1) dfsRecursive(startIndex, result);

        for (int i = 0; i < nodeCount; i++) {
            if (table[i].getExtra() == 0) {
                dfsRecursive(i, result);
            }
        }

        return result;
    }

    private LinkedList<String> dfsRecursive(int startNode, LinkedList<String> result) {
        table[startNode].setExtra(1);
        result.add(table[startNode].getName());

        for (int i = 0; i < nodeCount; i++) {
            if (matrix[startNode][i] != 0 && table[i].getExtra() != 1) {
                dfsRecursive(i, result);
            }
        }
        return result;
    }


    @Override
    public void shortestPathUnweighted(String startNode) throws Exception {
        int startIndex = find(startNode);
        if (startIndex == -1) throw new Exception("Node " + startNode + " does not exist");
        reset();
        Queue<Integer> queue = new LinkedList<>();
        table[startIndex].setDistance(0);
        queue.add(startIndex);

        while (!queue.isEmpty()) {
            int current = queue.remove();
            for (int i = 0; i < nodeCount; i++) {
                if (matrix[current][i] != 0 && table[i].getDistance() == INF) {
                    table[i].setDistance(table[current].getDistance() + 1);
                    table[i].setPredecessor(current);
                    queue.add(i);
                }
            }
        }
    }

    @Override
    public boolean shortestPathPositiveWeights(String startNode) {
        reset();
        int startIndex = find(startNode);
        if (startIndex == -1) return false;

        Node start = new Node(startNode, 0);
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        table[startIndex].setDistance(0);
        priorityQueue.add(start);

        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.remove();
            int v = find(current.getName());

            if (table[v].getExtra() == 0) {
                table[v].setExtra(1);
                for (int w = 0; w < nodeCount; w++) {
                    if (matrix[v][w] != 0) {
                        float cost = matrix[v][w];
                        if (cost < 0) {
                            return false;
                        }
                        if (table[w].getDistance() > table[v].getDistance() + cost) {
                            table[w].setDistance(table[v].getDistance() + cost);
                            table[w].setPredecessor(v);
                            priorityQueue.add(new Node(table[w].getName(), table[w].getDistance()));
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean shortestPathNegativeWeights(String startNode) {
        int startIndex = find(startNode);
        if (startIndex == -1) {
            System.out.println("Node " + startNode + " does not exist");
            return false;
        }

        reset();
        table[startIndex].setDistance(0);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startIndex);
        table[startIndex].setExtra(table[startIndex].getExtra() + 1);

        while (!queue.isEmpty()) {
            int v = queue.remove();
            table[v].setExtra(table[v].getExtra() + 1);

            if (table[v].getExtra() > 2 * nodeCount) {
                System.out.println("Negative cost cycle detected!");
                return false;
            }

            for (int w = 0; w < nodeCount; w++) {
                if (matrix[v][w] != 0) {
                    float cost = matrix[v][w];
                    if (table[w].getDistance() > table[v].getDistance() + cost) {
                        table[w].setDistance(table[v].getDistance() + cost);
                        table[w].setPredecessor(v);
                        if (!queue.contains(w)) {
                            queue.add(w);
                            table[w].setExtra(table[w].getExtra() + 1);
                        } else {
                            table[w].setExtra(table[w].getExtra() + 2);
                        }
                    }
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
            for (int v = 0; v < nodeCount; v++) {
                if (matrix[u][v] != 0) {
                    inDegree[v]++;
                }
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

            for (int v = 0; v < nodeCount; v++) {
                if (matrix[u][v] != 0) {
                    if (--inDegree[v] == 0) {
                        queue.add(v);
                    }
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
            if (table[u].getDistance() != INF) {
                for (int v = 0; v < nodeCount; v++) {
                    if (matrix[u][v] != 0) {
                        float newDist = table[u].getDistance() + matrix[u][v];
                        if (newDist < table[v].getDistance()) {
                            table[v].setDistance(newDist);
                            table[v].setPredecessor(u);
                        }
                    }
                }
            }
        }
        return true;
    }
}
