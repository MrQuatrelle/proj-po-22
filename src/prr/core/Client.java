package prr.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.ArrayList;
import java.util.LinkedList;

import prr.core.Notification;
import prr.core.Terminal.TerminalStatus;

public class Client implements Serializable {

    enum ClientType {
        NORMAL,
        GOLD,
        PREMIUM,
    }

    enum Path{
        DEFAULT,
    }

    private final String _key;
    private final String _name;
    private final long _ssNum;
    private ClientType _type;
    private LinkedList<Notification> _notifications;
    private boolean _receiveNotifications;
    private final ArrayList<Terminal> _terminals;

    private Path _path;


    private Client(String key, String name, long ss, ClientType type, ArrayList<Terminal> terminals) {
        _key = key;
        _name = name;
        _ssNum = ss;
        _type = type;
        _notifications = new LinkedList<Notification>();
        _terminals = (terminals != null) ? terminals : new ArrayList<>();
        _path = Path.DEFAULT;
    }

    public Client(String key, String name, long ss) {
        this(key, name, ss, ClientType.NORMAL, null);
    }

    public String toString() {
        var out = new StringBuilder("CLIENT");
        out.append("|"); out.append(_key);
        out.append("|"); out.append(_name);
        out.append("|"); switch (_type) {
            case NORMAL -> out.append("NORMAL");
            case GOLD -> out.append("GOLD");
            case PREMIUM -> out.append("PREMIUM");
        }
        out.append("|"); if (_receiveNotifications) out.append("YES");
            else out.append("NO");
        out.append("|"); out.append(this.countActiveTerminals());
        // out.append("|"); out.append(this.getPaidAmount());
        out.append("|"); out.append(0);
        // out.append("|"); out.append(this.getDebtAmount());
        out.append("|"); out.append(0);
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

    public ClientType getType() {
        return _type;
    }

    public void setType(ClientType t) {
        _type = t;
    }

    public boolean getReceiveNotifications() {
        return _receiveNotifications;
    }

    public void setReceiveNotifications(boolean b) {
        _receiveNotifications = b;
    }

    public void registerNotification(Notification n) {
        _notifications.add(n);
    }

    public Collection<Notification> getAndDeleteAllNotifications() {
        var out = new LinkedList<Notification>(_notifications);
        _notifications.clear();
        return out;
    }

    public void changePath(Path newPath){
        _path = newPath;
    }

    public Collection<Terminal> getTerminals() {
        return new ArrayList<>(_terminals);
    }

    private int countActiveTerminals() {
        int out = 0;
        for (Terminal t : _terminals) {
            if (t.getStatus() != TerminalStatus.OFF)
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

    // FIXME: uncomment this section of the code when notifications are implemented
    /*
    public ArrayList<Payment> getAllPayments() {
        var out = new ArrayList<Payment>();
        for (Terminal t : _terminals) {
            out.addAll(t.getAllPayments());
        }
        return out;
    }

    private double getPaidAmount() {
        double paid = 0;
        for (Terminal t : _terminals) {
            for (Payment p : t.getAllPayments()) {
                if (p.isPaid()) paid += p.getAmount();
            }
        }
        return paid;
    }

    private double getDebtAmount() {
        double debt = 0;
        for (Terminal t : _terminals) {
            for (Payment p : t.getAllPayments()) {
                if (p.isPaid()) debt += p.getAmount();
            }
        }
        return debt;
    }
    */
}
