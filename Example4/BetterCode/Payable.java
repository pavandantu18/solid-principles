package Example4.BetterCode;

// ISP: Every card can make payments, so this is the only interface
// that all card types must implement. Nothing extra is forced on anyone.
public interface Payable {
    void makePayment(double amount);
}
