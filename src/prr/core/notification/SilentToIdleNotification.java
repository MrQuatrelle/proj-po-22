package prr.core.notification;

public class SilentToIdleNotification extends Notification {
    public SilentToIdleNotification(String key) {
        super(NotificationType.S2I, key);
    }
}
