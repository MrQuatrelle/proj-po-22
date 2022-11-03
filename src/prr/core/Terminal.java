package prr.core;

import prr.core.exception.InexistentKeyException;
import prr.core.exception.NoVideoSupportException;
import prr.core.exception.UnavailableTerminalException;

import java.io.Serial;
import java.io.Serializable;

import java.util.*;


public abstract class Terminal implements Serializable {

    /*enum ComType{
        VOICE,
        VIDEO,
    }*/

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202208091753L;
    protected String _type;
    private final String _key;
    private final Client _client;
    private final Set<String> _friendlyKeys;
    private final List<String> _keysToNotify;

    private final Network _network;
    protected TerminalState _state;
    private final ArrayList<Payment> _payments;

    private InteractiveCommunication _currentCommunication;


    Terminal (String key, Client client, Network network) {
        _type = "BASIC";
        _key = key;
        _client = client;
        _state = new IdleState(this);
        _friendlyKeys = new HashSet<>();
        _network = network;
        _keysToNotify = new LinkedList<>();
        _payments = new ArrayList<>();
    }

    public String getKey() {
        return _key;
    }

    public static boolean isValidKey(String key){
        return key.matches("[0-9]+") && key.length() == 6;
    }

    public String getStatus() {
        return _state.toString();
    }

    Network getNetwork() {
        return _network;
    }

    Client getClient() {
        return _client;
    }

    String getClientKey(){
        return _client.getKey();
    }

    InteractiveCommunication getCommunication(){
        return _currentCommunication;
    }

    public void setStatus(String s) {
        _state.notifyClients(s);
        switch (s) {
            case "OFF" -> _state = new OffState(this);
            case "IDLE" -> _state = new IdleState(this);
            case "SILENCE" -> _state = new SilentState(this);
            case "BUSY" -> _state = new BusyState(this);
        }
    }

    public void setCurrentCommunication(InteractiveCommunication com){
        _currentCommunication = com;
    }

    public String toString() {
        var out = new StringBuilder(_type + "|" + _key + "|" + _client.getKey() + "|" +
                _state.toString() + "|" + getBalancePaid() + "|" + getBalanceDebts());
        for (String f: _friendlyKeys) {
            out.append("|"); out.append(f);
        }
        return new String(out);
    }

    @Override
    public int hashCode() {
        return _key.hashCode();
    }

    public int getBalancePaid() {
        //FIXME: Implement when payments get implemented
        return 0;
    }

    int getBalanceDebts() {
        //FIXME: Implement when payments get implemented
        return 0;
    }

    void addClientToNotify(String key) {
        _keysToNotify.add(key);
    }

    List<String> getClientsToNotify() {
        return _keysToNotify;
    }

    public void addFriend(String fk) throws InexistentKeyException {
        if (_network.hasTerminalKey(fk))
            _friendlyKeys.add(fk);
        else throw new InexistentKeyException(fk);
    }

    public void addPayment(Payment p){
        _payments.add(p);
    }
    public void removeFriend(String fk) throws InexistentKeyException {
        if (_network.hasTerminalKey(fk))
        _friendlyKeys.add(fk);
        else throw new InexistentKeyException(fk);
    }

    public void makeVoiceCall(String t) throws InexistentKeyException, UnavailableTerminalException,
            NoVideoSupportException {
        _state.makeVoiceCall(t);

    }

    void acceptVoiceCall() throws UnavailableTerminalException {
        _state.acceptVoiceCall();
    }

    public double endOngoingCommunication(int size) throws InexistentKeyException {
       return  _state.endOngoingCommunication(size);
    }

    public int getNumberOfCommunications() {
        return 0;
    }
    /**
     * Checks if this terminal can end the current interactive communication.
     *
     * @return true if this terminal is busy (i.e., it has an active interactive communication) and
     *          it was the originator of this communication.
     **/
    public boolean canEndCurrentCommunication() {
        return _state.canEndCurrentCommunication();
    }

    /**
     * Checks if this terminal can start a new communication.
     *
     * @return true if this terminal is neither off neither busy, false otherwise.
     **/
    public boolean canStartCommunication() {
        return _state.canStartCommunication();
    }

    public abstract void makeVideoCall(String receiver) throws NoVideoSupportException, InexistentKeyException, UnavailableTerminalException;
    protected abstract void acceptVideoCall() throws NoVideoSupportException, UnavailableTerminalException;


}


