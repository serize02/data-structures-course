package priority_queue;

public class ElementNotFoundException extends Exception {
  public ElementNotFoundException() {
    super("Element not found in the priority queue.");
  }
}