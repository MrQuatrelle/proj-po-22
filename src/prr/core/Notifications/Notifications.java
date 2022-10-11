package prr.core.Notifications;

public abstract class Notifications {
    enum Path{
        Email,
        Sms,
        TextMessage,
    }

    private String _terminalKey;
    private Path _path;

    public Notifications(String terminalKey, Path path) {
        _terminalKey = terminalKey;
        _path = path;
    }

}
