package Example5.ProblematicCode;

// Low-level module: sends order confirmation emails.
// Another concrete class with a specific channel tied to it.
public class EmailService {
    public void sendConfirmation(String orderId) {
        System.out.println("Email: Order confirmation sent for order [" + orderId + "]");
    }
}
