package Example5.BetterCode;

// Main is the "composition root" — the only place that knows about concrete classes.
// All wiring of abstractions to implementations happens here.
// OrderService itself never sees concrete class names.
public class Main {
    public static void main(String[] args) {

        System.out.println("=== Setup 1: MySQL + Email ===");
        OrderService service1 = new OrderService(
            new MySQLOrderRepository(),
            new EmailNotificationService()
        );
        service1.placeOrder("ORD-001", "Laptop");

        System.out.println();

        // Swap to PostgreSQL + SMS — OrderService code is UNTOUCHED
        System.out.println("=== Setup 2: PostgreSQL + SMS ===");
        OrderService service2 = new OrderService(
            new PostgreSQLOrderRepository(),
            new SMSNotificationService()
        );
        service2.placeOrder("ORD-002", "Phone");
    }
}
