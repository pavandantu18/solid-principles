package Example4.ProblematicCode;

// ISP VIOLATION: BasicCard only supports payments,
// but is FORCED to implement every method in the fat CreditCard interface.
// Unused methods throw exceptions or are left empty — polluting the class
// with behaviour it should never have.
public class BasicCard implements CreditCard {

    @Override
    public void makePayment(double amount) {
        System.out.println("BasicCard: Payment of $" + amount);
    }

    // NOT SUPPORTED — but forced to implement
    @Override
    public void earnRewards(double amount) {
        throw new UnsupportedOperationException("BasicCard does not support rewards.");
    }

    @Override
    public void redeemRewards(int points) {
        throw new UnsupportedOperationException("BasicCard does not support rewards.");
    }

    @Override
    public void accessLounge() {
        throw new UnsupportedOperationException("BasicCard does not have lounge access.");
    }

    @Override
    public void getTravelInsurance() {
        throw new UnsupportedOperationException("BasicCard does not have travel insurance.");
    }

    @Override
    public void generateExpenseReport() {
        throw new UnsupportedOperationException("BasicCard does not have expense reporting.");
    }

    @Override
    public void addEmployeeCard(String employeeName) {
        throw new UnsupportedOperationException("BasicCard does not support employee cards.");
    }
}
