package Example5.BetterCode;

// DIP: High-level module — contains only business logic.
// It depends exclusively on abstractions (OrderRepository, NotificationService),
// never on any concrete class. The actual implementations are injected from outside
// via the constructor (Constructor Injection).
//
// Benefits:
//   1. Swapping MySQL → PostgreSQL requires zero changes here.
//   2. Swapping Email → SMS requires zero changes here.
//   3. Unit tests can inject mock implementations without touching real infrastructure.
//   4. Business logic is fully decoupled from infrastructure details.
public class OrderService {

    private final OrderRepository orderRepository;
    private final NotificationService notificationService;

    // Dependencies are injected — OrderService never creates its own dependencies.
    // It does not know or care whether the repository is MySQL or PostgreSQL,
    // or whether the notification is Email or SMS.
    public OrderService(OrderRepository orderRepository, NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }

    public void placeOrder(String orderId, String item) {
        System.out.println("OrderService: Placing order [" + orderId + "]...");

        // Calling through abstractions — completely decoupled from low-level details
        orderRepository.saveOrder(orderId, item);
        notificationService.sendConfirmation(orderId);

        System.out.println("OrderService: Order [" + orderId + "] placed successfully.");
    }
}
