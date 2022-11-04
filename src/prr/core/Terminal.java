package prr.core;

import prr.core.exception.*;

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
    private final Set<String> _keysToNotify;

    private final Network _network;
    protected TerminalState _state;
    private final ArrayList<Payment> _payments;

    private InteractiveCommunication _currentCommunication;


    Terminal (String key, Client client, Network network) {
        _type = "BASIC";
        _key = key;
        _client = client;
        _state = new IdleState(this);
        _friendlyKeys = new TreeSet<>();
        _network = network;
        _keysToNotify = new HashSet<>();
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

    public Client getClient() {
        return _client;
    }

    String getClientKey(){
        return _client.getKey();
    }

    Payment getPayment(int id) throws InexistentPaymentException {
        for (Payment p : _payments){
            if (p.getId() == id){
                return p;
            }
        }
        throw new InexistentPaymentException(id);
    }

    public InteractiveCommunication getCommunication() throws NoOngoingCommunicationException {
        if (_currentCommunication!= null){
            return _currentCommunication;
        }
        throw new NoOngoingCommunicationException();
    }

    public void setStatus(String s) {
        _state.notifyClients(s);
        switch (s) {
            case "OFF" -> _state = new OffState(this);
            case "IDLE" -> _state = new IdleState(this);
            case "SILENCE" -> _state = new SilentState(this);
            case "BUSY" -> _state = new BusyState(this, _state);
        }
    }

    void setCurrentCommunication(InteractiveCommunication com){
        _currentCommunication = com;
    }

    public String toString() {
        var out = new StringJoiner("|");
        out.add(_type)
           .add(_key)
           .add(_client.getKey())
           .add(_state.toString())
           .add(String.valueOf(Math.round(getBalancePaid())))
           .add(String.valueOf(Math.round(getBalanceDebts())));
        if (!_friendlyKeys.isEmpty()) {
            var buffer = new StringJoiner(",");
            for (String fk : _friendlyKeys)
                buffer.add(fk);
            out.add(buffer.toString());
        }
        return out.toString();
    }

    @Override
    public int hashCode() {
        return _key.hashCode();
    }

    public double getBalancePaid() {
        double res = 0;
        for(Payment p : _payments){
            if(p.isPaid()){
                res += p.getCost();
            }
        }
        return res;
    }

    public double getBalanceDebts() {
        double res = 0;
        for(Payment p : _payments){
            if(!p.isPaid()){
                res += p.getCost();
            }
        }
        return res;
    }

    void addClientToNotify(String key) {
        _keysToNotify.add(key);
    }

    List<String> getClientsToNotify() {
        return new ArrayList<>(_keysToNotify);
    }

    void flushClientsToNotify() {
        _keysToNotify.clear();
    }

    public void addFriend(String fk) throws InexistentKeyException {
        if (!_network.hasTerminalKey(fk))
            throw new InexistentKeyException(fk);
        else if(!_key.equals(fk))
            _friendlyKeys.add(fk);
    }

    public void removeFriend(String fk) throws InexistentKeyException {
        if (_network.hasTerminalKey(fk))
        _friendlyKeys.remove(fk);
        else throw new InexistentKeyException(fk);
    }

    public boolean hasFriend(String fk){
        return _friendlyKeys.contains(fk);
    }
    public void addPayment(Payment p){
        _payments.add(p);
    }

    public boolean canPerformPayment(int id, Terminal t) throws InexistentPaymentException {
        return (_network.getCommunication(id).getSender() == t && !(_network.getCommunication(id).getState())
                && !(getPayment(id).isPaid()));
    }
    public void performPayment(int id) throws InexistentPaymentException {
        for (Payment p : _payments)
            if (p.getId() == id) {
                p.pay();
                return;
        }
        throw new InexistentPaymentException(id);
    }

    public void makeVoiceCall(String t) throws InexistentKeyException, UnavailableTerminalException,
            NoVideoSupportException {
        _state.makeVoiceCall(t);

    }

    void acceptVoiceCall(VoiceCommunication communication) throws UnavailableTerminalException {
        _state.acceptVoiceCall(communication);
    }

    public void makeTextCommunication(String destinationKey, String message) throws InexistentKeyException, UnavailableTerminalException {
        _state.makeTextCommunication(destinationKey, message);
    }

    void acceptTextCommunication(TextCommunication communication) throws UnavailableTerminalException {
        _state.acceptTextCommunication(communication);
    }
    public double endOngoingCommunication(int size) throws InexistentKeyException, NoOngoingCommunicationException {
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
    protected abstract void acceptVideoCall(VideoCommunication communication) throws NoVideoSupportException, UnavailableTerminalException;


}


