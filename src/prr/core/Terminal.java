package prr.core;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

public class Terminal implements Serializable /* FIXME maybe add more interfaces */{

    public enum TerminalStatus {
        OFF,
        SILENT,
        IDLE,
        BUSY,
    }

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202208091753L;
    private final Long _key;
    private final String _clientKey;
    private TerminalStatus _status;
    private final Set<String> _friendlyKeys;
    // private InteractiveCommunication _currCommunication;

    private Terminal (Long key, String clientKey, TerminalStatus status, Set<String> friendlyKeys) {
        _key = key;
        _clientKey = clientKey;
        _status = status;
        _friendlyKeys = (friendlyKeys != null) ? friendlyKeys : new HashSet<>();
    }

    public Terminal (Long key, String clientKey) {
        this(key, clientKey, TerminalStatus.IDLE, null);
    }

    public Long getKey() {
        return _key;
    }

    public TerminalStatus getStatus() {
        return _status;
    }

    void setStatus(TerminalStatus s) {
        _status = s;
    }

    @Override
    public String toString() {
        var buffer = new StringBuilder("BASIC");
        buffer.append("|"); buffer.append(_key);
        buffer.append("|"); buffer.append(_clientKey);
        buffer.append("|"); switch (_status) {
            case OFF    -> buffer.append("OFF");
            case SILENT -> buffer.append("SILENCE");
            case IDLE   -> buffer.append("IDLE");
            case BUSY   -> buffer.append("BUSY");
        }
        buffer.append("|"); buffer.append(this.getBalancePaid());
        buffer.append("|"); buffer.append(this.getBalanceDebts());
        for (String f: _friendlyKeys) {
            buffer.append("|"); buffer.append(f);
        }
        return new String(buffer);
    }

    public

    double getBalancePaid() {
        //FIXME: Implement when payments get implemented
        return 0;
    }

    double getBalanceDebts() {
        //FIXME: Implement when payments get implemented
        return 0;
    }

    void addFriend(String fk) {
        _friendlyKeys.add(fk);
    }

    public void makeVoiceCall() {
        // public VoiceCommunication makeVoiceCall() {
        if(this.canStartCommunication()) {
            _status = TerminalStatus.BUSY;
            /** FIXME: Uncomment and probably edit this when communications are implemented
             * return new VoiceCommunication(...)
             */
        }
    }

    void acceptVoiceCall() {
        // TODO
        _status = TerminalStatus.BUSY;
    }

    public void makeVideoCall(Terminal receiver) {
        //TODO
        //It probably will just return an exception, since it is a basic terminal
    }

    void acceptVideoCall(Terminal caller) {
        //TODO
        //It probably will just return an exception, since it is a basic terminal
    }

    public void endOngoingCommunication(int size) {
        //TODO
        _status = TerminalStatus.IDLE;
    }

    /**
     * Checks if this terminal can end the current interactive communication.
     *
     * @return true if this terminal is busy (i.e., it has an active interactive communication) and
     *          it was the originator of this communication.
     **/
    public boolean canEndCurrentCommunication() {
        /** FIXME: Uncomment when communications are implemented
         * if (_currCommunication != null)
         *      return (_currCommunication.provenienceKey() == _key);
         * return false;
         */
        return true;
    }

    /**
     * Checks if this terminal can start a new communication.
     *
     * @return true if this terminal is neither off neither busy, false otherwise.
     **/
    public boolean canStartCommunication() {
        /** FIXME: Uncomment when communications are implemented
         * return (_currCommunication == null && _status != TerminalStatus.OFF);
         */
        return true;
    }

}
