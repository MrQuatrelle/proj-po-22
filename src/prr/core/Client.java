package prr.core;

import java.util.Collection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import  java.io.Serializable;

import prr.core.Terminal.TerminalStatus;

public class Client implements Serializable{

    enum ClientType {
        NORMAL,
        GOLD,
        PREMIUM,
    }

    private static final long serialVersionUID = 202208091753L;
    private final String _key;
    private final String _name;
    private final long _ssNum;
    private ClientType _type;
    private boolean _receiveNotifications;
    private final ArrayList<Terminal> _terminals;

    private Client(String key, String name, long ss, ClientType type, ArrayList<Terminal> terminals) {
        _key = key;
        _name = name;
        _ssNum = ss;
        _type = type;
        _terminals = (terminals != null) ? terminals : new ArrayList<>();
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

    public List<Terminal> getTerminals() {
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
}
