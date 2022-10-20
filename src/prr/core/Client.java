package prr.core;

import java.util.ArrayList;
import java.util.List;
import  java.io.Serializable;

import prr.core.Terminal.Status;
import prr.core.exception.UnchangedNotificationException;

public class Client implements Serializable{

    enum Type {
        NORMAL,
        GOLD,
        PREMIUM,
    }

    private static final long serialVersionUID = 202208091753L;
    private final String _key;
    private final String _name;
    private final long _ssNum;
    private Type _type;
    private boolean _receiveNotifications;
    private final ArrayList<Terminal> _terminals;

    private Client(String key, String name, long ss, Type type, ArrayList<Terminal> terminals) {
        _key = key;
        _name = name;
        _ssNum = ss;
        _type = type;
        _receiveNotifications = true;
        _terminals = (terminals != null) ? terminals : new ArrayList<>();
    }

    public Client(String key, String name, long ss) {
        this(key, name, ss, Type.NORMAL, null);
    }

    public String toString() {
        var out = new StringBuilder("CLIENT" + "|" + _key + "|" + _name + "|" + _ssNum + "|" + _type + "|");
        if (_receiveNotifications) out.append("YES"); else out.append("NO");
        out.append("|" + countActiveTerminals() + "|" + 0 + "|" + 0);
        return new String(out);
    }

    public String getKey() {
        return new String(_key);
    }

    public String getName() {
        return new String(_name);
    }

    public long getSsNum() {
        return _ssNum;
    }

    public Type getType() {
        return _type;
    }

    public void setType(Type t) {
        _type = t;
    }

    public void setNotification(boolean b) throws UnchangedNotificationException {
        if (b == _receiveNotifications)
            throw new UnchangedNotificationException();
        _receiveNotifications = b;
    }

    public List<Terminal> getTerminals() {
        return new ArrayList<>(_terminals);
    }

    private int countActiveTerminals() {
        int out = 0;
        for (Terminal t : _terminals) {
            if (t.getStatus() != Status.OFF)
                out++;
        }
        return out;
    }

    public void addTerminal(Terminal t) {
        _terminals.add(t);
    }

    long getPaymentValue() {
        return 0;
    }

    long getDebtValue() {
        return 0;
    }
}
