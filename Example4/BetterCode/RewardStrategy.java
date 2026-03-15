package Example4.BetterCode;

// Strategy Pattern: Defines the algorithm contract for calculating rewards.
// Different cards can plug in different reward strategies at runtime
// without changing card logic — combining ISP with the Strategy pattern.
public interface RewardStrategy {
    int calculate(double amount);
    String rewardType();
}
