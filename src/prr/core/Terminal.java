package prr.core;

import prr.core.exception.InexistentKeyException;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public abstract class Terminal implements Serializable {

    public enum Status {
        OFF,
        SILENT,
        IDLE,
        BUSY,
    }


    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202208091753L;
    protected String _type;
    protected final String _key;
    protected final String _clientKey;
    protected Status _status;
    protected final Set<String> _friendlyKeys;

    private final Network _network;
    // private InteractiveCommunication _currCommunication;

    Terminal (String key, String clientKey, Status status, Set<String> friendlyKeys, Network network) {
        _type = "BASIC";
        _key = key;
        _clientKey = clientKey;
        _status = status;
        _friendlyKeys = (friendlyKeys != null) ? friendlyKeys : new HashSet<>();
        _network = network;
    }

    public Terminal (String key, String clientKey, Network network) {
        this(key, clientKey, Status.IDLE, null, network);
    }

    public String getKey() {
        return _key;
    }

    public static boolean isValidKey(String key){
        return key.matches("[0-9]+") && key.length() == 6;
    }
    public Status getStatus() {
        return _status;
    }

    public void setStatus(Status s) {
        _status = s;
    }

    public String toString() {
        var out = new StringBuilder(_type + "|" + _key + "|" + _clientKey + "|" + _status + "|"
                + getBalancePaid() + "|" + getBalanceDebts());
        for (String f: _friendlyKeys) {
            out.append("|"); out.append(f);
        }
        return new String(out);
    }

    public

    int getBalancePaid() {
        //FIXME: Implement when payments get implemented
        return 0;
    }

    int getBalanceDebts() {
        //FIXME: Implement when payments get implemented
        return 0;
    }

    public void addFriend(String fk) throws InexistentKeyException {
        if (_network.hasTerminalKey(fk))
            _friendlyKeys.add(fk);
        else throw new InexistentKeyException(fk);
    }

    public void removeFriend(String fk) throws InexistentKeyException {
        if (_network.hasTerminalKey(fk))
        _friendlyKeys.add(fk);
        else throw new InexistentKeyException(fk);
    }

    public void makeVoiceCall() {
        if(this.canStartCommunication()) {
            _status = Status.BUSY;
        }
    }

    void acceptVoiceCall() {
        // TODO
        _status = Status.BUSY;
    }

    public void endOngoingCommunication(int size) {
        //TODO
        _status = Status.IDLE;
    }

    public int getNumberOfCommunications(){
        return 0;
    }
    /**
     * Checks if this terminal can end the current interactive communication.
     *
     * @return true if this terminal is busy (i.e., it has an active interactive communication) and
     *          it was the originator of this communication.
     **/
    public boolean canEndCurrentCommunication() {
        /* FIXME: Uncomment when communications are implemented*/
        return _status == Status.BUSY;
    }

    /**
     * Checks if this terminal can start a new communication.
     *
     * @return true if this terminal is neither off neither busy, false otherwise.
     **/
    public boolean canStartCommunication() {
        return _status != Status.BUSY;
    }

}
