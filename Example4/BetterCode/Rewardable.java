package Example4.BetterCode;

// ISP: Only cards that support rewards implement this interface.
// BasicCard is never forced to touch this.
public interface Rewardable {
    void earnRewards(double amount);
    void redeemRewards(int points);
}
