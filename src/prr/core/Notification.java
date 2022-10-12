package prr.core;

public abstract class Notification {

    public enum NotificationType{
        S2I,
        O2S,
        B2I,
        O2I,
    }
    private String _terminalKey;
    private NotificationType _notification;

    public Notification(String terminalKey, NotificationType notification) {
        _terminalKey = terminalKey;
        _notification = notification;
    }

    @Override
    public String toString() {
        var out = new StringBuilder();
        switch (_notification){
            case S2I -> out.append("S2I");
            case B2I -> out.append("B2I");
            case O2I -> out.append("O2I");
            case O2S -> out.append("O2S");
        }
        out.append("|");
        out.append(_terminalKey);
        return new String(out);
    }
}
