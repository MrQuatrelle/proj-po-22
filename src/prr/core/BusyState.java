package prr.core;

import prr.core.exception.UnavailableTerminalException;

public class BusyState extends TerminalState {
    BusyState(Terminal t) {
        super(t);
    }

    @Override
    public String toString() {
        return "BUSY";
    }

    @Override
    boolean canEndCurrentCommunication() {
        return true;
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
    void makeVoiceCall(String t) {
        //Do nothing
    }

    @Override
    void acceptVoiceCall() throws UnavailableTerminalException {
        //Do nothing
        throw new UnavailableTerminalException(_terminal.getKey(), toString());
    }

    @Override
    void makeVideoCall(String t) {
        //Do nothing
    }

    @Override
    void acceptVideoCall() throws UnavailableTerminalException {
        throw new UnavailableTerminalException(_terminal.getKey(), toString());
        //Do nothing
    }
}
