package Example4.BetterCode;

// ISP: Only travel-tier cards implement this interface.
// Business or basic cards are never polluted with travel methods.
public interface TravelBenefits {
    void accessLounge();
    void getTravelInsurance();
}
