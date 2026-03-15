package Example3.BetterCode;

// LSP: Rectangle implements Shape with its own independent width and height.
// No subclass can interfere with how width and height behave here.
// The contract of getArea() == width * height is always honoured.
public class Rectangle implements Shape {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getArea() {
        return width * height;
    }
}
