package Example5.BetterCode;

// DIP: Alternative low-level detail for SMS notifications.
// Swapping Email to SMS = swap this class at the injection point in Main.
// OrderService requires zero changes for this swap.
public class SMSNotificationService implements NotificationService {

    @Override
    public void sendConfirmation(String orderId) {
        System.out.println("SMS: Order confirmation sent for order [" + orderId + "]");
    }
}
