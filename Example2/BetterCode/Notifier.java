package Example2.BetterCode;

// How to improve?
// We should try to think that if further alternation in the 
// requirements can unnecessary impact code of a class, it is violating OCP

// So, we can write an interface to represent any notification
// Individual type of notification implementing the interface.
// Notification sender instead of being tightly coupled to type od notifications we now depend on Notification interface.
// Even if we introduce new types of notifications or remove any older one, the sender class is not impacted.

// It is a good idea to depend on abstractions rather than concrete classes.

// OCP: Define a contract for sending notifications.
// New notification types can be added by implementing this interface
// without modifying any existing class.
public interface Notifier {
    void send(String message);
}
