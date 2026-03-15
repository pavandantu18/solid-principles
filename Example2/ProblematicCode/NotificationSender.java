package Example2.ProblematicCode;

import java.util.List;

public class NotificationSender {
    public void sendNotification(List<NotificationType> notificationTypes, String message) {
        for (NotificationType type : notificationTypes) {
            switch (type) {
                case SMS:
                    type.sendSMSNotification(message);
                    break;
                case EMAIL:
                    type.sendEmailNotification(message);
                    break;
                case PUSH:
                    type.sendPushNotification(message);
                    break;
                case WHATSAPP:
                    type.sendWhatsAppNotification(message);
                    break;
            }
        }
    }
}
