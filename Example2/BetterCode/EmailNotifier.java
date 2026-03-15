package Example2.BetterCode;

// OCP: Encapsulates Email sending logic. Closed for modification;
// changes to Email behavior are contained here and affect nothing else.
public class EmailNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}
