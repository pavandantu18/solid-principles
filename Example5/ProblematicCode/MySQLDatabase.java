package Example5.ProblematicCode;

// Low-level module: handles direct MySQL database operations.
// This is a concrete class with a specific technology tied to it.
public class MySQLDatabase {
    public void saveOrder(String orderId, String item) {
        System.out.println("MySQL: Saving order [" + orderId + "] for item: " + item);
    }
}
