package prr.core;

import prr.core.exception.InexistentKeyException;
import prr.core.exception.NoVideoSupportException;
import prr.core.exception.UnavailableTerminalException;

public class IdleState extends TerminalState {

    IdleState(Terminal t) {
        super(t);
    }

    @Override
    public String toString() {
        return "IDLE";
    }

    @Override
    boolean canEndCurrentCommunication() {
        return false;
    }

    @Override
    boolean canStartCommunication() {
        return true;
    }

    @Override
    void endOngoingCommunication() {

    }

    @Override
    void makeVoiceCall(String t) throws InexistentKeyException, UnavailableTerminalException, NoVideoSupportException {
        _terminal.getNetwork().getTerminal(t).acceptVideoCall();
    }

    @Override
    void acceptVoiceCall() {
        //FIXME wating for the implementation of communications
    }

    @Override
    void makeVideoCall(String t) throws InexistentKeyException, UnavailableTerminalException, NoVideoSupportException {
        _terminal.getNetwork().getTerminal(t).acceptVideoCall();
    }

    @Override
    void acceptVideoCall(/*communication*/) {
        //FIXME wating for the implementation of communications
    }
}
