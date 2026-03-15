package Example5.ProblematicCode;

// Dependency Inversion Principle (DIP):
// "High-level modules should not depend on low-level modules.
//  Both should depend on abstractions."
//
// DIP VIOLATION: OrderService is a high-level module (business logic),
// but it directly creates and depends on concrete low-level classes
// MySQLDatabase and EmailService.
//
// Problems this causes:
//   1. To switch from MySQL to PostgreSQL, this class must be modified.
//   2. To switch from Email to SMS, this class must be modified.
//   3. Unit testing is impossible without hitting a real database and email server.
//   4. High-level business logic is tightly coupled to low-level infrastructure details.
public class OrderService {

    // Hard-coded dependencies on concrete classes — the DIP violation
    private MySQLDatabase database = new MySQLDatabase();
    private EmailService emailService = new EmailService();

    public void placeOrder(String orderId, String item) {
        System.out.println("OrderService: Placing order [" + orderId + "]...");

        // Directly calling low-level concrete implementations
        database.saveOrder(orderId, item);
        emailService.sendConfirmation(orderId);

        System.out.println("OrderService: Order [" + orderId + "] placed successfully.");
    }

    public static void main(String[] args) {
        OrderService service = new OrderService();
        service.placeOrder("ORD-001", "Laptop");
        // Changing to PostgreSQL or SMS requires editing this class directly.
    }
}
