package Example4.BetterCode;

// ISP: BusinessCard implements Payable, Rewardable, and ExpenseManagement.
// It is NOT forced to implement TravelBenefits — lounge access is irrelevant here.
// Strategy Pattern: Reward strategy is injected, making it easy to switch reward plans.
public class BusinessCard implements Payable, Rewardable, ExpenseManagement {

    private final RewardStrategy rewardStrategy;

    public BusinessCard(RewardStrategy rewardStrategy) {
        this.rewardStrategy = rewardStrategy;
    }

    @Override
    public void makePayment(double amount) {
        System.out.println("BusinessCard: Payment of $" + amount + " processed.");
        earnRewards(amount);
    }

    @Override
    public void earnRewards(double amount) {
        int earned = rewardStrategy.calculate(amount);
        System.out.println("BusinessCard: Earned " + earned + " " + rewardStrategy.rewardType() + ".");
    }

    @Override
    public void redeemRewards(int points) {
        System.out.println("BusinessCard: Redeemed " + points + " " + rewardStrategy.rewardType() + " as statement credit.");
    }

    @Override
    public void generateExpenseReport() {
        System.out.println("BusinessCard: Monthly expense report generated for all transactions.");
    }

    @Override
    public void addEmployeeCard(String employeeName) {
        System.out.println("BusinessCard: Employee card issued to " + employeeName + ".");
    }
}
