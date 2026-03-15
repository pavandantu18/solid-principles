package Example5.BetterCode;

// DIP: Abstraction for sending notifications.
// OrderService depends on this interface, not on EmailService or SMSService directly.
// New notification channels can be added without touching OrderService.
public interface NotificationService {
    void sendConfirmation(String orderId);
}
