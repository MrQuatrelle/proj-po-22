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
    double endOngoingCommunication(int size) {
        return 0;
    }

    @Override
    void makeVoiceCall(String t) throws InexistentKeyException, UnavailableTerminalException, NoVideoSupportException {
        _terminal.getNetwork().getTerminal(t).acceptVoiceCall();
        _terminal.setCurrentCommunication(new VoiceCommunication(_terminal.getNetwork().getNrOfCommunications(),
                _terminal.getNetwork().getTerminal(_terminal.getKey()), _terminal.getNetwork().getTerminal(t),true));
        _terminal.getNetwork().incrementCommunicationNr();
    }

    @Override
    void acceptVoiceCall() {
        _terminal.setStatus("BUSY");
    }

    @Override
    void makeVideoCall(String t) throws InexistentKeyException, UnavailableTerminalException, NoVideoSupportException {
        _terminal.getNetwork().incrementCommunicationNr();
        _terminal.getNetwork().getTerminal(t).acceptVideoCall();
        _terminal.setCurrentCommunication(new VideoCommunication(_terminal.getNetwork().getNrOfCommunications(),
                _terminal.getNetwork().getTerminal(_terminal.getKey()), _terminal.getNetwork().getTerminal(t),true));
        _terminal.getNetwork().addCommunication(new VideoCommunication(_terminal.getNetwork().getNrOfCommunications(),
                _terminal.getNetwork().getTerminal(_terminal.getKey()), _terminal.getNetwork().getTerminal(t),true));
    }

    @Override
    void acceptVideoCall() {
        _terminal.setStatus("BUSY");
    }

    @Override
    void notifyClients(String s) {
        //Do nothing
    }
}
