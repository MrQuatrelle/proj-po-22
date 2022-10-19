package prr.core;

import java.io.Serializable;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import prr.core.exception.*;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    /****ATTRIBUTES****/
    private final Map<String, Client> _clients;
    private final Map<String, Terminal> _terminals;


    /****CONSTRUCTOR****/
    public Network() {
        _clients = new TreeMap<>();
        _terminals = new TreeMap<>();
    }


    /****METHODS****/
    /** Adds a client to the network, if the key doesn't exist yet */
    public void addClient(String key, String name, long ss) throws DuplicateException {
        if (!_clients.containsKey(key))
            _clients.put(key, new Client(key, name, ss));
        else throw new DuplicateException(key);
    }

    /** @return container with all the clients of the network */
    public List<Client> getAllClients() {
        return new ArrayList<Client>(_clients.values());
    }

    public Client getClient(String key) throws InexistentKeyException {
        if (_clients.containsKey(key)) {
            return _clients.get(key);
        }
        throw new InexistentKeyException(key);
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

    public void setClientNotification(String key, boolean b) {
        _clients.get(key).setNotification(b);
    }

    boolean hasTerminalKey(String key) {
        return _terminals.containsKey(key);
    }
    public Terminal getTerminal(String key) throws InexistentKeyException{
        if (_terminals.containsKey(key))
            return _terminals.get(key);
        else throw new InexistentKeyException(key);
    }
    public void addFriend(String terminal, String friend) throws InexistentKeyException {
        if (_terminals.containsKey(terminal)) {
            if (_terminals.containsKey(friend))
                _terminals.get(terminal).addFriend(friend);
            else throw new InexistentKeyException(friend);
        }
        else throw new InexistentKeyException(terminal);
    }

    public void addTerminal(String type, String key, String client)
            throws UnallowedTypeException, InexistentKeyException, UnallowedKeyException, DuplicateException {
        if (!Terminal.isValidKey(key)) throw new UnallowedKeyException(key);
        if (_terminals.containsKey(key)) throw new DuplicateException(key);
        Terminal t;
        switch (type) {
            case "BASIC" -> t = new Terminal(key, client, this);
            case "FANCY" -> t = new FancyTerminal(key, client, this);
            default -> throw new UnallowedTypeException(key);
        }
        if (_clients.containsKey(client)) {
            _terminals.put(key, t);
            _clients.get(client).addTerminal(t);
        }
        else throw new InexistentKeyException(client);
    }

    void addParsedTerminal(String type, String key, String client, String status)
            throws UnallowedTypeException, InexistentKeyException, InvalidStatusException, UnallowedKeyException, DuplicateException {
        addTerminal(type, key, client);
        var terminal = _terminals.get(key);
        switch(status) {
            case "SILENCE" -> terminal.setStatus(Terminal.Status.SILENT);
            case "OFF" -> terminal.setStatus(Terminal.Status.OFF);
            default -> {
                if (!status.equals("ON"))
                    throw new InvalidStatusException(status);
            }
        }
    }

    public List<String> getAllTerminalStrings() {
        var out = new ArrayList<String>();
        for (Terminal t: _terminals.values())
           out.add(t.toString());
        return out;
    }

    public List<Terminal> getAllUnusedTerminals(){
        var out = new ArrayList<Terminal>();
        for (Terminal i : _terminals.values()){
            if (i.getNumberOfCommunications() == 0){
                out.add(i);
            }
        }
        return out;
    }
    /**
     * Read text input file and create corresponding domain entities.
     *
     * @param filename name of the text input file
     * @throws UnrecognizedEntryException if some entry is not correct
     * @throws IOException if there is an IO error while processing the text file
     */
    void importFile(String filename) throws UnrecognizedEntryException, IOException, UnallowedTypeException, DuplicateException, UnallowedKeyException, ImportFileException {
        var parser = new Parser(this);
        parser.parseFile(filename);
    }
}

