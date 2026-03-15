package Example2.BetterCode;

// OCP: Encapsulates WhatsApp sending logic. Closed for modification;
// changes to WhatsApp behavior are contained here and affect nothing else.
public class WhatsAppNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("WhatsApp: " + message);
    }
}
