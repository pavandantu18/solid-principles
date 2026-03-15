package Example4.BetterCode;

public class Main {
    public static void main(String[] args) {

        // BasicCard — only pays, no rewards forced on it
        Payable basic = new BasicCard();
        basic.makePayment(50.0);

        System.out.println();

        // PremiumCard — pays + cashback rewards
        PremiumCard premium = new PremiumCard(new CashbackStrategy());
        premium.makePayment(200.0);
        premium.redeemRewards(150);

        System.out.println();

        // TravelCard — pays + miles rewards + travel perks
        TravelCard travel = new TravelCard(new MilesStrategy());
        travel.makePayment(300.0);
        travel.accessLounge();
        travel.getTravelInsurance();
        travel.redeemRewards(600);

        System.out.println();

        // BusinessCard — pays + points rewards + corporate expense tools
        BusinessCard business = new BusinessCard(new PointsStrategy());
        business.makePayment(500.0);
        business.addEmployeeCard("Alice");
        business.generateExpenseReport();
        business.redeemRewards(1500);

        System.out.println();

        // Strategy can be swapped at runtime — TravelCard using PointsStrategy instead of Miles
        System.out.println("--- TravelCard with swapped PointsStrategy ---");
        TravelCard flexTravel = new TravelCard(new PointsStrategy());
        flexTravel.makePayment(100.0);
    }
}
