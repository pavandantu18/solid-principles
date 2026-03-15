package Example5.BetterCode;

// DIP: Abstraction for data persistence.
// Both the high-level OrderService and low-level database classes
// depend on this interface — not on each other.
// Switching databases means providing a new implementation, not editing OrderService.
public interface OrderRepository {
    void saveOrder(String orderId, String item);
}
