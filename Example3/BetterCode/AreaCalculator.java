package Example3.BetterCode;

// LSP: AreaCalculator depends only on the Shape abstraction.
// Any Shape — Rectangle, Square, or a future Triangle — can be substituted here
// and the result will always be correct. No contract is ever broken.
public class AreaCalculator {

    public void printArea(Shape shape) {
        System.out.println("Area: " + shape.getArea());
    }

    public static void main(String[] args) {
        AreaCalculator calc = new AreaCalculator();

        calc.printArea(new Rectangle(5, 3)); // Area: 15 (correct)
        calc.printArea(new Square(4));        // Area: 16 (correct)

        // Both substituted Shape successfully — LSP is satisfied.
    }
}
