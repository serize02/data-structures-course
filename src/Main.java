import gps.GPSSystem;

public class Main {
    public static void main(String[] args) {

        GPSSystem gps = new GPSSystem();
        gps.addCity("New York");
        gps.addCity("Chicago");
        gps.addCity("Los Angeles");
        gps.addCity("Houston");
        gps.addCity("Phoenix");
        gps.addCity("Denver");
        gps.addCity("Seattle");
        gps.addCity("Miami");

        gps.addEdge("New York", "Chicago", 790.0f);
        gps.addEdge("New York", "Miami", 1280.0f);
        gps.addEdge("Chicago", "Denver", 1000.0f);
        gps.addEdge("Chicago", "Houston", 1080.0f);
        gps.addEdge("Houston", "Phoenix", 1175.0f);
        gps.addEdge("Houston", "Los Angeles", 2000.0f);
        gps.addEdge("Denver", "Phoenix", 830.0f);
        gps.addEdge("Denver", "Seattle", 1300.0f);
        gps.addEdge("Phoenix", "Los Angeles", 370.0f);

        gps.compare("New York", "Los Angeles");
        gps.getShortestPathAcyclic("New York");
    }
}