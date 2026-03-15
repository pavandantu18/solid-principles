package Example5.BetterCode;

// DIP: An alternative low-level detail for PostgreSQL.
// Swapping from MySQL to PostgreSQL = swap this class at the injection point in Main.
// OrderService requires zero changes for this swap.
public class PostgreSQLOrderRepository implements OrderRepository {

    @Override
    public void saveOrder(String orderId, String item) {
        System.out.println("PostgreSQL: Saving order [" + orderId + "] for item: " + item);
    }
}
