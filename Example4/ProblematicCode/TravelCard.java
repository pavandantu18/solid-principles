package Example4.ProblematicCode;

// ISP VIOLATION: TravelCard has no use for business features,
// but is still forced to implement generateExpenseReport() and addEmployeeCard().
public class TravelCard implements CreditCard {

    @Override
    public void makePayment(double amount) {
        System.out.println("TravelCard: Payment of $" + amount);
    }

    @Override
    public void earnRewards(double amount) {
        System.out.println("TravelCard: Earned " + (int)(amount * 2) + " miles.");
    }

    @Override
    public void redeemRewards(int points) {
        System.out.println("TravelCard: Redeemed " + points + " miles for a flight upgrade.");
    }

    @Override
    public void accessLounge() {
        System.out.println("TravelCard: Lounge access granted.");
    }

    @Override
    public void getTravelInsurance() {
        System.out.println("TravelCard: Travel insurance activated.");
    }

    // NOT SUPPORTED — but forced to implement due to fat interface
    @Override
    public void generateExpenseReport() {
        throw new UnsupportedOperationException("TravelCard does not support expense reports.");
    }

    @Override
    public void addEmployeeCard(String employeeName) {
        throw new UnsupportedOperationException("TravelCard does not support employee cards.");
    }
}
