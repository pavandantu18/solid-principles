package Example2.BetterCode;

import java.util.List;

// OCP: This class is closed for modification. It works against the Notifier interface,
// so adding a new notification type (e.g. SlackNotifier) requires zero changes here.
public class NotificationSender {
    public void sendNotification(List<Notifier> notifiers, String message) {
        for (Notifier notifier : notifiers) {
            notifier.send(message);
        }
    }
}
