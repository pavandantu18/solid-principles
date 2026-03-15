package Example4.BetterCode;

// Strategy: 2 air miles earned per dollar spent.
// Used by TravelCard.
public class MilesStrategy implements RewardStrategy {

    @Override
    public int calculate(double amount) {
        return (int)(amount * 2);
    }

    @Override
    public String rewardType() {
        return "air miles";
    }
}
