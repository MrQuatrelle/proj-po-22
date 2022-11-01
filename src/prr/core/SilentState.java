package prr.core;

import prr.core.exception.InexistentKeyException;
import prr.core.exception.NoVideoSupportException;
import prr.core.exception.UnavailableTerminalException;

public class SilentState extends TerminalState {
    SilentState(Terminal t) {
        super(t);
    }

    @Override
    public String toString() {
        return "SILENCE";
    }

    @Override
    boolean canEndCurrentCommunication() {
        return false;
    }

    @Override
    boolean canStartCommunication() {
        return false;
    }

    @Override
    void endOngoingCommunication() {
        //Do nothing
    }

    @Override
    void makeVoiceCall(String t) throws InexistentKeyException, UnavailableTerminalException {
        _terminal.getNetwork().getTerminal(t).acceptVoiceCall();
        //Do nothing
    }

    @Override
    void acceptVoiceCall() throws UnavailableTerminalException {
        //Do nothing
        throw new UnavailableTerminalException(_terminal.getKey(), toString());
    }

    @Override
    void makeVideoCall(String t) throws InexistentKeyException, UnavailableTerminalException, NoVideoSupportException {
        _terminal.getNetwork().getTerminal(t).acceptVideoCall();
    }

    @Override
    void acceptVideoCall() throws UnavailableTerminalException {
        throw new UnavailableTerminalException(_terminal.getKey(), toString());
    }
}
