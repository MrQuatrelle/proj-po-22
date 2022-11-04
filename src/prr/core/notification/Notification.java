package prr.core.notification;

public abstract class Notification {
    private final NotificationType _type;
    private final String _key;

    Notification(NotificationType type, String key) {
        _type = type;
        _key = key;
    }

    @Override
    public String toString() {
        return "" + _type + "|" + _key;
    }

    public String getKey() {
        return _key;
    }
}
