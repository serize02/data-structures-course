import gps.GPSSystem;
import priority_queue.EmptyPriorityQueueException;
import searchengine.Engine;
import network.Net;

public class Main {
    public static void main(String[] args) throws EmptyPriorityQueueException {

        // gps
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

        //search engine
        String[] documents = {
                "It was the best of times, it was the worst of times, it was the age of wisdom, it was the age of foolishness, it was the epoch of belief, it was the epoch of incredulity, it was the season of Light, it was the season of Darkness, it was the spring of hope, it was the winter of despair.",

                "Call me Ishmael. Some years ago—never mind how long precisely—having little or no money in my purse, and nothing particular to interest me on shore, I thought I would sail about a little and see the watery part of the world. It is a way I have of driving off the spleen and regulating the circulation.",

                "In a hole in the ground there lived a hobbit. Not a nasty, dirty, wet hole, but a hobbit-hole, and that means comfort. This hobbit was a very well-to-do hobbit, and his name was Bilbo Baggins. The Hobbit was a story of adventures and unexpected journeys.",

                "The quick brown fox jumps over the lazy dog. This pangram contains every letter of the English alphabet. It is often used to test typing skills or display fonts. Practicing this sentence can improve speed and accuracy in typing.",

                "Pattern recognition is essential in computer science and artificial intelligence. Detecting a pattern quickly can lead to optimized algorithms and efficient problem solving. Machine learning models rely heavily on finding patterns within large datasets.",

                "The pattern in the data was difficult to find, but with persistence and advanced analysis, the pattern emerged clearly. Understanding this pattern allowed researchers to predict future outcomes with high accuracy.",

                "This text contains the phrase many times: Not all those who wander are lost. Not all those who wander are lost. Not all those who wander are lost. Not all those who wander are lost. Not all those who wander are lost.",

                "Another large text filled with words and the phrase occurring intermittently among them. Not all those who wander are lost here and there, it shows up frequently, but sometimes it hides. Searching for the phrase can be a challenging task in this large body of text.",

                "A very large document with the phrase repeated many times to make the search algorithm work harder. Not all those who wander are lost. Not all those who wander are lost. Not all those who wander are lost. Not all those who wander are lost."
        };
        String pattern = "Not all those who wander are lost";

        Engine engine = new Engine(documents, pattern);
        engine.compareMethods();
        engine.priority();

        // social netwoek
        Net network = new Net();

        network.addUser("Alice Johnson");
        network.addUser("Alice Morales");
        network.addUser("Alice Rivera");

        network.addUser("Bob Smith");
        network.addUser("Bob Carter");
        network.addUser("Bob Gonzalez");

        network.addUser("Charlie Davis");
        network.addUser("Charlie Nguyen");
        network.addUser("Charlie Flores");

        network.addUser("Diana Brown");
        network.addUser("Diana Ortega");
        network.addUser("Diana Torres");

        network.addUser("Eve Thompson");
        network.addUser("Eve Delgado");
        network.addUser("Eve Navarro");

        network.addUser("Frank Miller");
        network.addUser("Frank Ramirez");
        network.addUser("Frank Gomez");

        network.addUser("Grace Allen");
        network.addUser("Heidi Silva");
        network.addUser("Ivan Martinez");
        network.addUser("Judy Lopez");

        // Relaciones (amistades)
        network.addFriend("Alice Johnson", "Bob Smith");
        network.addFriend("Alice Morales", "Bob Carter");
        network.addFriend("Alice Rivera", "Bob Gonzalez");

        network.addFriend("Alice Johnson", "Charlie Davis");
        network.addFriend("Alice Morales", "Charlie Nguyen");
        network.addFriend("Alice Rivera", "Charlie Flores");

        network.addFriend("Bob Smith", "Diana Brown");
        network.addFriend("Bob Carter", "Diana Ortega");
        network.addFriend("Bob Gonzalez", "Diana Torres");

        network.addFriend("Charlie Davis", "Eve Thompson");
        network.addFriend("Charlie Nguyen", "Eve Delgado");
        network.addFriend("Charlie Flores", "Eve Navarro");

        network.addFriend("Diana Brown", "Frank Miller");
        network.addFriend("Diana Ortega", "Frank Ramirez");
        network.addFriend("Diana Torres", "Frank Gomez");

        network.addFriend("Eve Thompson", "Grace Allen");
        network.addFriend("Eve Delgado", "Heidi Silva");
        network.addFriend("Eve Navarro", "Ivan Martinez");

        network.addFriend("Frank Miller", "Judy Lopez");
        network.addFriend("Frank Ramirez", "Grace Allen");
        network.addFriend("Frank Gomez", "Heidi Silva");

        network.addFriend("Grace Allen", "Alice Rivera");
        network.addFriend("Heidi Silva", "Alice Johnson");
        network.addFriend("Ivan Martinez", "Alice Morales");

        network.find("Alice");
        network.KFriends("Eve Navarro", 4);
        network.recommendation("Frank Miller");
    }
}