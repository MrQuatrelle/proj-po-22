package prr.core.notification;

import java.io.Serial;
import  java.io.Serializable;

public abstract class Notification implements Serializable {

    enum NotificationType {
        O2S,
        O2I,
        B2I,
        S2I,
    }

    @Serial
    private static final long serialVersionUID = 202211101013L;

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
