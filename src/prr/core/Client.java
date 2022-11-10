package prr.core;

import java.io.Serial;
import  java.io.Serializable;
import java.nio.MappedByteBuffer;
import java.util.*;

import prr.core.exception.UnchangedNotificationException;
import prr.core.notification.Notification;

public class Client implements Serializable {

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
    private ClientType _type;
    private boolean _receiveNotifications;
    private Map<String, Notification> _notifications;
    private final Map<String, Terminal> _terminals;

    private final Map<Integer, Communication> _communicationsFrom;
    private final Map<Integer, Communication> _communicationsTo;

    private final ArrayList<Payment> _clientPayments;

    Client(String key, String name, long ss) {
        _key = key;
        _name = name;
        _ssNum = ss;
        _type = new NormalType(this);
        _receiveNotifications = true;
        _terminals = new HashMap<>();
        _notifications = new HashMap<>();
        _communicationsFrom = new HashMap<>();
        _communicationsTo = new HashMap<>();
        _clientPayments = new ArrayList<>();
    }

    public String toString() {
        var out = new StringBuilder("CLIENT" + "|" + _key + "|" + _name + "|" + _ssNum + "|" + _type + "|");
        if (_receiveNotifications) out.append("YES"); else out.append("NO");
        out.append("|")
           .append(countTerminals())
           .append("|")
           .append(Math.round(getPaymentValue()))
           .append("|")
           .append(Math.round(getDebtValue()));
        return new String(out);
    }

    public String getKey() {
        return _key;
    }

    public void setType(ClientType t) {
        _type = t;
    }

    public String getTypeString(){
        return _type.toString();
    }

    public ClientType getType(){
        return _type;}
    public void setNotification(boolean b) throws UnchangedNotificationException {
        if (b == _receiveNotifications)
            throw new UnchangedNotificationException();
        _receiveNotifications = b;
    }

    boolean wantsNotifications() {
        return _receiveNotifications;
    }

    public List<Terminal> getTerminals() {
        return new ArrayList<>(_terminals.values());
    }

    public List<Communication> getAllReceivingCommunications() {
        return _communicationsTo.values().stream().toList();
    }

    public List<Communication> getAllSendingCommunications() {
        return _communicationsFrom.values().stream().toList();
    }

    private int countActiveTerminals() {
        int out = 0;
        for (Terminal t : _terminals.values()) {
            if (!Objects.equals(t.getStatus(), "OFF"))
                out++;
        }
        return out;
    }

    private int countTerminals() {
        int out = 0;
        for (Terminal t : _terminals.values()) {
            out++;
        }
        return out;
    }

    public void addTerminal(Terminal t) {
        _terminals.put(t.getKey(), t);
    }

    public void addComTo(Communication com){
        _communicationsTo.put(com.getId(), com);
    }

    public void addComFrom(Communication com){
        _communicationsFrom.put(com.getId(), com);
    }

    public void addPayment(Payment p){
        _clientPayments.add(p);
        _type.checkTypeUpdate(_communicationsFrom.get(p.getId()));
    }

    double getBalanceValue() {
        double res = 0;
        for (Payment p: _clientPayments) {
            if (p.isPaid())
                res += p.getCost();
            else
                res -= p.getCost();
        }
        return res;
    }
    double getPaymentValue() {
        double res = 0;
        for(Payment p : _clientPayments){
            if(p.isPaid()){
                res += p.getCost();
            }
        }
        return res;
    }

    double getDebtValue() {
        double res = 0;
        for(Payment p : _clientPayments){
            if(!p.isPaid()){
                res += p.getCost();
            }
        }
        return res;
    }

    void addNotification(Notification n) {
        _notifications.remove(n.getKey());
        _notifications.put(n.getKey(), n);
    }

    Collection<Notification> getNotifications() {
        var out = _notifications.values();
        _notifications = new HashMap<>();
        return out;
    }
}
