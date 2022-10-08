package prr.core;

import java.io.Serial;
import java.io.Serializable;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
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

    // FIXME define attributes
    private TerminalStatus _status;
    // FIXME define contructor(s)
    public Terminal () {
        _status = TerminalStatus.IDLE;
    }
    // FIXME define methods

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
