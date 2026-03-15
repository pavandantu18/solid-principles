package Example5.BetterCode;

// DIP: Low-level detail — depends on the OrderRepository abstraction, not the other way around.
// If MySQL-specific logic changes, only this class is affected. OrderService never knows.
public class MySQLOrderRepository implements OrderRepository {

    @Override
    public void saveOrder(String orderId, String item) {
        System.out.println("MySQL: Saving order [" + orderId + "] for item: " + item);
    }
}
