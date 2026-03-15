package Example4.ProblematicCode;

// ISP VIOLATION: BusinessCard has no use for travel features like lounge access
// or travel insurance, yet is forced to implement them from the fat interface.
public class BusinessCard implements CreditCard {

    @Override
    public void makePayment(double amount) {
        System.out.println("BusinessCard: Payment of $" + amount);
    }

    @Override
    public void earnRewards(double amount) {
        System.out.println("BusinessCard: Earned " + (int)(amount * 3) + " cashback points.");
    }

    @Override
    public void redeemRewards(int points) {
        System.out.println("BusinessCard: Redeemed " + points + " points as statement credit.");
    }

    // NOT SUPPORTED — but forced to implement due to fat interface
    @Override
    public void accessLounge() {
        throw new UnsupportedOperationException("BusinessCard does not have lounge access.");
    }

    @Override
    public void getTravelInsurance() {
        throw new UnsupportedOperationException("BusinessCard does not have travel insurance.");
    }

    @Override
    public void generateExpenseReport() {
        System.out.println("BusinessCard: Expense report generated for all transactions.");
    }

    @Override
    public void addEmployeeCard(String employeeName) {
        System.out.println("BusinessCard: Employee card added for " + employeeName + ".");
    }
}
