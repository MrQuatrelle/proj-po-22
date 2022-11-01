package prr.core;

import prr.core.exception.UnavailableTerminalException;

public class OffState extends TerminalState {
    OffState(Terminal t) {
        super(t);
    }

    @Override
    public String toString() {
        return "OFF";
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
    void makeVoiceCall(String t) {
        //Do nothing
    }

    @Override
    void acceptVoiceCall() throws UnavailableTerminalException {
        throw new UnavailableTerminalException(_terminal.getKey(), toString());
    }

    @Override
    void makeVideoCall(String t) {
        //Do nothing
    }

    @Override
    void acceptVideoCall() throws UnavailableTerminalException {
        throw new UnavailableTerminalException(_terminal.getKey(), toString());
    }
}
