package Example4.BetterCode;

// Strategy: 3 loyalty points earned per dollar spent.
// Can be swapped in for any Rewardable card without changing card code.
public class PointsStrategy implements RewardStrategy {

    @Override
    public int calculate(double amount) {
        return (int)(amount * 3);
    }

    @Override
    public String rewardType() {
        return "loyalty points";
    }
}
