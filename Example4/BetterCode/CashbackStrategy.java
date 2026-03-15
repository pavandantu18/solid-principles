package Example4.BetterCode;

// Strategy: 1% cashback on every dollar spent.
// Used by PremiumCard and BusinessCard.
public class CashbackStrategy implements RewardStrategy {

    @Override
    public int calculate(double amount) {
        return (int)(amount * 0.01 * 100); // returns cents as integer points
    }

    @Override
    public String rewardType() {
        return "cashback cents";
    }
}
