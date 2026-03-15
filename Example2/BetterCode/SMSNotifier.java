package Example2.BetterCode;

// OCP: Encapsulates SMS sending logic. Closed for modification;
// changes to SMS behavior are contained here and affect nothing else.
public class SMSNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}
