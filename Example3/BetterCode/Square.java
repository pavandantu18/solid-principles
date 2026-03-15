package Example3.BetterCode;

// LSP: Square implements Shape independently — it does NOT extend Rectangle.
// It enforces its own invariant (all sides equal) without touching Rectangle's contract.
// Both Rectangle and Square can now substitute Shape safely and correctly.
public class Square implements Shape {
    private int side;

    public Square(int side) {
        this.side = side;
    }

    @Override
    public int getArea() {
        return side * side;
    }
}
