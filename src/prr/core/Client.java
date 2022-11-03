package prr.core;

import java.io.Serial;
import java.util.*;
import  java.io.Serializable;

import prr.core.exception.UnchangedNotificationException;
import prr.core.notification.Notification;

public class Client implements Serializable{

    enum Type {
        NORMAL,
        GOLD,
        PLATINUM,
    }

    @Serial
    private static final long serialVersionUID = 202209231753L;
    private final String _key;
    private final String _name;
    private final long _ssNum;
    private Type _type;
    private boolean _receiveNotifications;
    private final List<Notification> _notifications;
    private final Set<Terminal> _terminals;

    private final ArrayList<Communication> _communicationsFrom;

    private final ArrayList<Communication> _communicationsTo;

    private Client(String key, String name, long ss, Type type, ArrayList<Terminal> terminals) {
        _key = key;
        _name = name;
        _ssNum = ss;
        _type = type;
        _receiveNotifications = true;
        _terminals = new HashSet<>();
        _notifications = new ArrayList<>();
        _communicationsFrom = new ArrayList<>();
        _communicationsTo = new ArrayList<>();
    }

    public Client(String key, String name, long ss) {
        this(key, name, ss, Type.NORMAL, null);
    }

    public String toString() {
        var out = new StringBuilder("CLIENT" + "|" + _key + "|" + _name + "|" + _ssNum + "|" + _type + "|");
        if (_receiveNotifications) out.append("YES"); else out.append("NO");
        out.append("|")
           .append(countActiveTerminals())
           .append("|")
           .append(0)
           .append("|")
           .append(0);
        return new String(out);
    }

    public String getKey() {
        return _key;
    }

    public void setType(Type t) {
        _type = t;
    }

    public Type getType(){
        return _type;
    }

    public void setNotification(boolean b) throws UnchangedNotificationException {
        if (b == _receiveNotifications)
            throw new UnchangedNotificationException();
        _receiveNotifications = b;
    }

    boolean wantsNotifications() {
        return _receiveNotifications;
    }
    public List<Terminal> getTerminals() {
        return new ArrayList<>(_terminals);
    }

    public List<String> getAllCommunicationToStrings() {
        var out = new ArrayList<String>();
        for (Communication c: _communicationsTo)
            out.add(c.toString());
        return out;
    }

    public List<String> getAllCommunicationFromStrings() {
        var out = new ArrayList<String>();
        for (Communication c: _communicationsFrom)
            out.add(c.toString());
        return out;
    }
    private int countActiveTerminals() {
        int out = 0;
        for (Terminal t : _terminals) {
            if (!Objects.equals(t.getStatus(), "OFF"))
                out++;
        }
        return out;
    }

    public void addTerminal(Terminal t) {
        _terminals.add(t);
    }

    public void addComTo(Communication com){
        _communicationsTo.add(com);
    }

    public void addComFrom(Communication com){
        _communicationsFrom.add(com);
    }

    long getPaymentValue() {
        return 0;
    }

    long getDebtValue() {
        return 0;
    }

    void addNotification(Notification n) {
        _notifications.add(n);
    }

    List<Notification> getNotifications() {
        var out = new ArrayList<>(_notifications);
        _notifications.clear();
        return out;
    }
}
