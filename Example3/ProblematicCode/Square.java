package Example3.ProblematicCode;

// LSP VIOLATION: Square extends Rectangle.
// A square must always have equal sides, so both setters force width == height.
// This BREAKS the contract that Rectangle promises:
//   rect.setWidth(5);
//   rect.setHeight(3);
//   rect.getArea() --> caller expects 15, but gets 9 (because setHeight also changed width).
// Square cannot fully substitute Rectangle — LSP is violated.
public class Square extends Rectangle {

    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;   // side-effect: silently changes height too
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
        this.width = height;   // side-effect: silently changes width too
    }
}
