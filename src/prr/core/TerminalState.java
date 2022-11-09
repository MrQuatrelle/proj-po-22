package prr.core;

import prr.core.exception.InexistentKeyException;
import prr.core.exception.NoOngoingCommunicationException;
import prr.core.exception.NoVideoSupportException;
import prr.core.exception.UnavailableTerminalException;

import java.io.Serial;
import java.io.Serializable;

public abstract class TerminalState implements Serializable {

    @Serial
    private static final long serialVersionUID = 202211011549L;
    protected Terminal _terminal;

    TerminalState(Terminal t) {
        _terminal = t;
    }
    public abstract String toString();
    abstract boolean canEndCurrentCommunication();
    abstract boolean canStartCommunication();
    abstract double endOngoingCommunication(int size) throws InexistentKeyException, NoOngoingCommunicationException;
    abstract void makeVoiceCall(String t) throws InexistentKeyException, UnavailableTerminalException,
            NoVideoSupportException;
    abstract void acceptVoiceCall(VoiceCommunication communication) throws UnavailableTerminalException;
    abstract void makeVideoCall(String t) throws InexistentKeyException, UnavailableTerminalException,
            NoVideoSupportException;
    abstract void acceptVideoCall(VideoCommunication communication) throws UnavailableTerminalException;
    abstract void notifyClients(String s);

    abstract void acceptTextCommunication(TextCommunication communication) throws UnavailableTerminalException;
    abstract void makeTextCommunication(String destinationKey, String message) throws InexistentKeyException,
            UnavailableTerminalException;
}
