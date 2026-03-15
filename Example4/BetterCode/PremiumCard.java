package Example4.BetterCode;

// ISP: PremiumCard implements only Payable and Rewardable — exactly what it offers.
// Strategy Pattern: The reward calculation algorithm is injected, so it can be
// swapped (e.g. from CashbackStrategy to PointsStrategy) without changing this class.
public class PremiumCard implements Payable, Rewardable {

    private final RewardStrategy rewardStrategy;

    public PremiumCard(RewardStrategy rewardStrategy) {
        this.rewardStrategy = rewardStrategy;
    }

    @Override
    public void makePayment(double amount) {
        System.out.println("PremiumCard: Payment of $" + amount + " processed.");
        earnRewards(amount);
    }

    @Override
    public void earnRewards(double amount) {
        int earned = rewardStrategy.calculate(amount);
        System.out.println("PremiumCard: Earned " + earned + " " + rewardStrategy.rewardType() + ".");
    }

    @Override
    public void redeemRewards(int points) {
        System.out.println("PremiumCard: Redeemed " + points + " " + rewardStrategy.rewardType() + ".");
    }
}
