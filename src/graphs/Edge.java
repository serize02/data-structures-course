package graphs;

public class Edge {

    private int destination;
    private float cost;

    public Edge(int destination, float cost) {
        this.destination = destination;
        this.cost = cost;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}

