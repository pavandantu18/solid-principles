package Example5.BetterCode;

// DIP: Low-level detail for email notifications.
// Implements NotificationService — the abstraction OrderService depends on.
public class EmailNotificationService implements NotificationService {

    @Override
    public void sendConfirmation(String orderId) {
        System.out.println("Email: Order confirmation sent for order [" + orderId + "]");
    }
}
