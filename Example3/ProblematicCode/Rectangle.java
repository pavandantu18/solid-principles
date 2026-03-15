package Example3.ProblematicCode;

// Liskov Substitution Principle (LSP):
// "Objects of a subclass should be substitutable for objects of the superclass
//  without altering the correctness of the program."
//
// In other words: wherever a parent type is expected, a child type must
// behave consistently — it must honour the contracts (pre/post conditions)
// of the parent. A subclass should NEVER break the behaviour that callers
// rely on when working through the parent reference.
//
// PROBLEMATIC: Rectangle is the base class.
// Callers assume: after setWidth(w) and setHeight(h), area == w * h.
public class Rectangle {
    protected int width;
    protected int height;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }
}
