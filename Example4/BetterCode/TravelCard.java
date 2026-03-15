package Example4.BetterCode;

// ISP: TravelCard implements Payable, Rewardable, and TravelBenefits.
// It is NOT forced to implement ExpenseManagement — that belongs to BusinessCard only.
// Strategy Pattern: Miles strategy is injected for reward calculation.
public class TravelCard implements Payable, Rewardable, TravelBenefits {

    private final RewardStrategy rewardStrategy;

    public TravelCard(RewardStrategy rewardStrategy) {
        this.rewardStrategy = rewardStrategy;
    }

    @Override
    public void makePayment(double amount) {
        System.out.println("TravelCard: Payment of $" + amount + " processed.");
        earnRewards(amount);
    }

    @Override
    public void earnRewards(double amount) {
        int earned = rewardStrategy.calculate(amount);
        System.out.println("TravelCard: Earned " + earned + " " + rewardStrategy.rewardType() + ".");
    }

    @Override
    public void redeemRewards(int points) {
        System.out.println("TravelCard: Redeemed " + points + " " + rewardStrategy.rewardType() + " for a flight upgrade.");
    }

    @Override
    public void accessLounge() {
        System.out.println("TravelCard: Airport lounge access granted.");
    }

    @Override
    public void getTravelInsurance() {
        System.out.println("TravelCard: Complimentary travel insurance activated.");
    }
}
