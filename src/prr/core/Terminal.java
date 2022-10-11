package prr.core;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

    public enum TerminalStatus {
        OFF,
        SILENT,
        IDLE,
        BUSY,
    }

    /** Serial number for serialization. */

    @Serial
    private static final long serialVersionUID = 202208091753L;
    private final String _key;
    private String _clientKey;
    private TerminalStatus _status;
    private Collection<String> _friendlyKeys;

    // FIXME define attributes
    // FIXME define contructor(s)

    private Terminal (String key, String clientKey, TerminalStatus status, Collection<String> friendlyKeys) {
        _key = new String(key);
        _clientKey = new String(clientKey);
        _status = status;
        _friendlyKeys = friendlyKeys;
    }
    public Terminal (String key, String clientKey) {
        this(key, clientKey, TerminalStatus.IDLE, new ArrayList<>());
    }

    public Terminal copy() {
        var t = new Terminal(_key, _clientKey);
        return t;
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
        // FIXME add implementation code
        return true;
    }

    /**
     * Checks if this terminal can start a new communication.
     *
     * @return true if this terminal is neither off neither busy, false otherwise.
     **/
    public boolean canStartCommunication() {
        // FIXME add implementation code
        return true;
    }
}
