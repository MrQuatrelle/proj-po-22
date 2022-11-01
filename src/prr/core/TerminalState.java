package prr.core;

import prr.core.exception.InexistentKeyException;
import prr.core.exception.NoVideoSupportException;
import prr.core.exception.UnavailableTerminalException;

public abstract class TerminalState {

    protected Terminal _terminal;

    TerminalState(Terminal t) {
        _terminal = t;
    }
    public abstract String toString();
    abstract boolean canEndCurrentCommunication();
    abstract boolean canStartCommunication();
    abstract void endOngoingCommunication();
    abstract void makeVoiceCall(String t) throws InexistentKeyException, UnavailableTerminalException, NoVideoSupportException;
    abstract void acceptVoiceCall() throws UnavailableTerminalException;
    abstract void makeVideoCall(String t) throws InexistentKeyException, UnavailableTerminalException, NoVideoSupportException;
    abstract void acceptVideoCall() throws UnavailableTerminalException;

}
