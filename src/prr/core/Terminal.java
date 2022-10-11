package prr.core;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
    private String _clientKey;
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

    public void setStatus(TerminalStatus s) {
        _status = s;
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

    public boolean startVoiceCommunication() {
    // public VoiceCommunication startVoiceCommunication() {
        if(this.canStartCommunication()) {
            _status = TerminalStatus.BUSY;
            /** FIXME: Uncomment and probably edit this when communications are implemented
             * return new VoiceCommunication(...)
             */
            return true;
        }
        return false;
    }
}
