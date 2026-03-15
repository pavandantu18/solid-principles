package Example3.BetterCode;

// LSP FIX: Instead of forcing an inheritance hierarchy between Rectangle and Square
// (which breaks the substitution contract), we extract a common abstraction.
//
// Each shape is only responsible for computing its own area using its own rules.
// No shared mutable state is forced on subclasses — so no contract can be broken.
public interface Shape {
    int getArea();
}
