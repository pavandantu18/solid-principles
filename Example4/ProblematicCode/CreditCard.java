package Example4.ProblematicCode;

// Interface Segregation Principle (ISP):
// "Clients should not be forced to depend on interfaces they do not use."
//
// A fat interface forces ALL card types to implement ALL features,
// even features that are irrelevant to them. This leads to empty or
// UnsupportedOperationException implementations — a clear ISP violation.
public interface CreditCard {
    void makePayment(double amount);

    // Rewards features — not all cards support this
    void earnRewards(double amount);
    void redeemRewards(int points);

    // Travel features — only travel cards support this
    void accessLounge();
    void getTravelInsurance();

    // Business features — only business cards support this
    void generateExpenseReport();
    void addEmployeeCard(String employeeName);
}
