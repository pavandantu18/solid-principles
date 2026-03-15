package Example2.BetterCode;

// OCP: Encapsulates Push notification logic. Closed for modification;
// changes to Push behavior are contained here and affect nothing else.
public class PushNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("Push: " + message);
    }
}
