package prr.core.notification;

public class BusyToIdleNotification extends Notification {
    public BusyToIdleNotification(String key) {
        super(NotificationType.B2I, key);
    }
}
