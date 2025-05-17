package graphs;


public class Node implements Comparable<Node> {

    private String name;
    private int extra;
    private int predecessor;
    private float distance;

    public Node(String name) {
        this.name = name;
        this.extra = 0;
        this.predecessor = -1;
        this.distance = Float.MAX_VALUE;
    }

    public Node() {
        this.distance = -1;
    }

    public Node(String name, float distance) {
        this.name = name;
        this.extra = 0;
        this.predecessor = -1;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }

    public int getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(int predecessor) {
        this.predecessor = predecessor;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Node other) {
        return Float.compare(distance, other.distance);
    }
}
