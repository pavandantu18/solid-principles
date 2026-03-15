package Example3.ProblematicCode;

// This method works perfectly for a real Rectangle but produces a WRONG result
// when a Square is passed in — demonstrating the LSP violation.
public class AreaCalculator {

    // Expects: after setWidth(5) and setHeight(3), area should be 15.
    public void printArea(Rectangle r) {
        r.setWidth(5);
        r.setHeight(3);

        // If r is a Square, width was overwritten to 3 inside setHeight().
        // So area = 3 * 3 = 9, not 15. The program is now INCORRECT.
        System.out.println("Expected area: 15, Actual area: " + r.getArea());
    }

    public static void main(String[] args) {
        AreaCalculator calc = new AreaCalculator();

        System.out.println("--- Rectangle ---");
        calc.printArea(new Rectangle()); // prints 15 (correct)

        System.out.println("--- Square (LSP violated) ---");
        calc.printArea(new Square());    // prints 9  (WRONG — contract broken)
    }
}
