package gps;

import graphs.AdjacencyListGraph;

import java.util.ArrayList;
import java.util.List;

public class GPSSystem {

    private final AdjacencyListGraph graph;

    public GPSSystem() {
        graph = new AdjacencyListGraph();
    }

    public void addCity(String city) {
        graph.addNode(city);
    }

    public void addEdge(String from, String to, float dist) {
        graph.addEdge(from, to, dist);
    }

    public String getShortestDistance(String from, String to) {
        if (graph.shortestPathPositiveWeights(from)) return graph.getPath(to);
        else return null;
    }

    public String getShortestPath(String from, String to) {
        graph.shortestPathUnweighted(from);
        return graph.getPath(to);
    }

    public void compare(String from, String to) {
        System.out.println(getShortestDistance(from, to) + " dist:" + graph.getDistance(to));
        System.out.println(getShortestPath(from, to) + " connections:" + graph.getDistance(to));
    }

    public void getShortestPathAcyclic(String from) {
        List<Float> distances = new ArrayList<>();
        if (graph.shortestPathAcyclic(from)) distances = graph.getDistances();
        System.out.println(distances);
    }

}
