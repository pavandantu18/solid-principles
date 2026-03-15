package Example4.BetterCode;

// ISP: BasicCard only implements Payable — the one feature it actually supports.
// No rewards, no travel, no business methods are forced onto this class.
public class BasicCard implements Payable {

    @Override
    public void makePayment(double amount) {
        System.out.println("BasicCard: Payment of $" + amount + " processed.");
    }
}
