package prr.core;

import java.io.Serial;
import java.io.Serializable;
import java.io.IOException;
import java.util.*;

import prr.core.exception.*;
import prr.core.notification.Notification;
import prr.core.notification.SilentToIdleNotification;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202208091753L;

    /****ATTRIBUTES****/
    private final Map<String, Client> _clients;
    private final Map<String, Terminal> _terminals;
    private int _nrOfCommunications = 0;
    private final Set<Communication> _allCommunications;

    /****CONSTRUCTOR****/
    public Network() {
        _clients = new TreeMap<>();
        _terminals = new TreeMap<>();
        _allCommunications = new HashSet<>();
    }


    /****METHODS****/
    /** Adds a client to the network, if the key doesn't exist yet
     * @param key specific client key
     * @param name client's name
     * @param ss client's NIF
     * @throws DuplicateException if the key already exists*/
    public void addClient(String key, String name, long ss) throws DuplicateException {
        if (!_clients.containsKey(key))
            _clients.put(key, new Client(key, name, ss));
        else throw new DuplicateException(key);
    }

    /** @return container with all the string representations of the clients of the network */
    public List<String> getAllClientStrings() {
        return _clients.values().stream()
                                .sorted(Comparator.comparing(client -> client.getKey().toLowerCase()))
                                .map(Client::toString)
                                .toList();
    }

    /** Gets client with given key
     * @param key specific client key
     * @return client with given key
     * @throws InexistentKeyException if the key doesn't exist
     */
    public String getClientString(String key) throws InexistentKeyException {
        if (_clients.containsKey(key)) {
            return _clients.get(key).toString();
        }
        throw new InexistentKeyException(key);
    }

    Client getClient(String key) throws InexistentKeyException {
        if (_clients.containsKey(key))
            return _clients.get(key);
        throw new InexistentKeyException(key);
    }

    public List<String> getCommunicationsOfClient(String key) throws InexistentKeyException {
        var buffer = new ArrayList<>(getClient(key).getAllSendingCommunications());
        buffer.addAll(getClient(key).getAllReceivingCommunications());
        return buffer.stream()
                     .sorted(Comparator.comparing(Communication::getId))
                     .map(Communication::toString)
                     .toList();
    }
    public List<String> getReceivingCommunicationsOfClient(String key) throws InexistentKeyException {
        return getClient(key).getAllReceivingCommunications().stream()
                                                             .map(Communication::toString)
                                                             .toList();
    }

    public List<String> getSendingCommunicationsOfClient(String key) throws InexistentKeyException {
        return getClient(key).getAllSendingCommunications().stream()
                .map(Communication::toString)
                .toList();
    }

    void addCommunication(Communication com){
        _allCommunications.add(com);
    }

    Communication getCommunication(int id){
        for (Communication c: _allCommunications)
            if (c.getId() == id)
                return c;
        return null;
    }
    public List<String> getAllCommunicationStrings() {
        return _allCommunications.stream()
                                 .sorted(Comparator.comparing(Communication::getId))
                                 .map(Communication::toString)
                                 .toList();

    }

    /** Gets the value of all the payments done by the client with the given key
     * @param key client's specific key
     * @return payments value if key exists, -1 if the key doesn't exist
     */
    public double getClientPaymentValue(String key) {
        if (_clients.containsKey(key))
            return _clients.get(key).getPaymentValue();
        return -1;
    }

    /** Gets the value of all the debts of the client with the given key
     * @param key client's specific key
     * @return debts value if key exists, -1 if the key doesn't exist
     */
    public double getDebtPaymentValue(String key) {
        if (_clients.containsKey(key))
            return _clients.get(key).getDebtValue();
        return -1;
    }

    public List<String> getClientsWithDebtsString(){
        ArrayList<String> out = new ArrayList<>();
        for (Client c : _clients.values()){
            if (c.getDebtValue() > 0){
                out.add(c.toString());
            }
        }
        return out;
    }
    public List<String> getClientsWithoutDebtsString() {
        ArrayList<String> out = new ArrayList<>();
        for (Client c: _clients.values()) {
            if (c.getDebtValue() == 0) {
                out.add(c.toString());
            }
        }
        return out;
    }

    /** Sets the notifications of the client with the given key on or off
     * @param key client's specific key
     * @param b true to turn on, false for turn of
     */
    public void setClientNotification(String key, boolean b) throws UnchangedNotificationException {
        _clients.get(key).setNotification(b);
    }

    public List<String> getClientNotifications(String key) {
        var c = _clients.get(key);
        var buffer = c.getNotifications();
        for(Notification n: buffer)
            _terminals.get(n.getKey()).flushClientsToNotify();
        var out = buffer.stream().map(Notification::toString).toList();
        if (c.wantsNotifications()) {
            return out;
        }
        return null;
    }
    void notifyClient(String key, Notification n) {
        _clients.get(key).addNotification(n);
    }
    /** Checks if the terminal exists
     * @param key Terminal key
     * @return true if the terminal exists, false if it doesn't
     */
    boolean hasTerminalKey(String key) {
        return _terminals.containsKey(key);
    }

    /** Gets the terminal with the given key
     * @param key Terminal key
     * @return terminal with the given key
     * @throws InexistentKeyException if Terminal doesn't exist
     */
    public Terminal getTerminal(String key) throws InexistentKeyException{
        if (_terminals.containsKey(key))
            return _terminals.get(key);
        else throw new InexistentKeyException(key);
    }

    /** Adds friend to the list of friends of the given terminal
     * @param terminal Terminal to add friend
     * @param friend Terminal added as friend
     * @throws InexistentKeyException if one of the keys doesn't exist
     */
    public void addFriend(String terminal, String friend) throws InexistentKeyException {
        if (_terminals.containsKey(terminal)) {
            if (_terminals.containsKey(friend))
                _terminals.get(terminal).addFriend(friend);
            else throw new InexistentKeyException(friend);
        }
        else throw new InexistentKeyException(terminal);
    }

    /** Adds a terminal to the network, if it doesn't exist yet
     * @param type Terminal type
     * @param key Terminal key
     * @param client Client key
     * @throws UnallowedTypeException if the type is not basic nor fancy
     * @throws InexistentKeyException if the client's key doesn't exist
     * @throws UnallowedKeyException if the terminal key doesn't exist
     * @throws DuplicateException if the terminal already exists
     */
    public void addTerminal(String type, String key, String client)
            throws UnallowedTypeException, InexistentKeyException, UnallowedKeyException, DuplicateException {
        if (!Terminal.isValidKey(key)) throw new UnallowedKeyException(key);
        if (_terminals.containsKey(key)) throw new DuplicateException(key);
        Terminal t;
        switch (type) {
            case "BASIC" -> t = new BasicTerminal(key, _clients.get(client), this);
            case "FANCY" -> t = new FancyTerminal(key, _clients.get(client), this);
            default -> throw new UnallowedTypeException(key);
        }
        if (_clients.containsKey(client)) {
            _terminals.put(key, t);
            _clients.get(client).addTerminal(t);
        }
        else throw new InexistentKeyException(client);
    }

    /** To be used by parser to insert a terminal with all the information, instead of attributing
     * each element one by one
     * @param type Terminal type
     * @param key Terminal key
     * @param client Client key
     * @param status Terminal status
     * @throws UnallowedTypeException if the type is not basic nor fancy
     * @throws InexistentKeyException if the client's key doesn't exist
     * @throws InvalidStatusException if the status does not correspond to one of the terminal status
     * @throws UnallowedKeyException if the terminal key doesn't exist
     * @throws DuplicateException if the terminal already exists
     */
    void addParsedTerminal(String type, String key, String client, String status)
            throws UnallowedTypeException, InexistentKeyException, InvalidStatusException, UnallowedKeyException, DuplicateException {
        addTerminal(type, key, client);
        var terminal = _terminals.get(key);
        switch(status) {
            case "SILENCE", "OFF" -> terminal.setStatus(status);
            default -> {
                if (!status.equals("ON"))
                    throw new InvalidStatusException(status);
            }
        }
    }

    /** Gets all the terminals in the network to string
     * @return all the terminals in to string form
     */
    public List<String> getAllTerminalStrings() {
        var out = new ArrayList<String>();
        for (Terminal t: _terminals.values())
           out.add(t.toString());
        return out;
    }

    /** Gets all the terminals in the network with no communications done to string
     * @return all the terminals in to string form
     */
    public List<Terminal> getAllUnusedTerminals(){
        var out = new ArrayList<Terminal>();
        for (Terminal i : _terminals.values()){
            if (i.getNumberOfCommunications() == 0){
                out.add(i);
            }
        }
        return out;
    }

    public void incrementCommunicationNr(){
        _nrOfCommunications++;
    }

    public int getNrOfCommunications(){
        return _nrOfCommunications;
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

