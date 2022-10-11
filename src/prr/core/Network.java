package prr.core;

import java.io.Serializable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import prr.core.exception.UnrecognizedEntryException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    // FIXME define attributes
    private final HashMap<String, Client> _clients;
    private final HashMap<Long, Terminal> _terminals;


    public Network() {
        _clients = new HashMap<>();
        _terminals = new HashMap<>();
    }



    // FIXME define methods
    /** Adds a client to the network, if the key doesn't exist yet */
    public void addClient(String key, String name, long ss) {
        if (!_clients.containsKey(key))
            _clients.put(key, new Client(key, name, ss));
    }

    /** @return container with all the clients of the network */
    public List<String> getAllClientStrings() {
        var out = new ArrayList<String>();
        for (Client c : _clients.values()) {
            out.add(c.toString());
        }
        return out;
    }

    public String getClientString(String key) {
        if (_clients.containsKey(key)) {
            return _clients.get(key).toString();
        }
        return null;
    }

    public long getClientPaymentValue(String key) {
        if (_clients.containsKey(key))
            return _clients.get(key).getPaymentValue();
        return -1;
    }

    public long getDebtPaymentValue(String key) {
        if (_clients.containsKey(key))
            return _clients.get(key).getDebtValue();
        return -1;
    }

    public void setClientNotifications(String key, boolean b) {
        var c = _clients.get(key);
        if (c.getReceiveNotifications() != b) {
           c.setReceiveNotifications(b);
        }
    }

    public void addTerminal(Long key, String type) {
        if (type.equals("BASIC"))
            _terminals.put(key, new Terminal(key, null));
        else _terminals.put(key, new FancyTerminal(key, null));
    }

    public List<String> getAllTerminalStrings() {
        var out = new ArrayList<String>();
        for (Terminal t: _terminals.values())
           out.add(t.toString());
        return out;
    }

    /**
     * Read text input file and create corresponding domain entities.
     *
     * @param filename name of the text input file
     * @throws UnrecognizedEntryException if some entry is not correct
     * @throws IOException if there is an IO error while processing the text file
     */
    void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
        //FIXME implement method
    }
}

