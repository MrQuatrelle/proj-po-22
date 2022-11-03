package prr.core;

import prr.core.exception.InexistentKeyException;
import prr.core.exception.NoVideoSupportException;
import prr.core.exception.UnavailableTerminalException;
import prr.core.notification.SilentToIdleNotification;

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
    double endOngoingCommunication(int size) {
        return 0;
    }

    @Override
    void makeVoiceCall(String t) throws InexistentKeyException, UnavailableTerminalException {
        _terminal.getNetwork().getTerminal(t).acceptVoiceCall();
        _terminal.setCurrentCommunication(new VoiceCommunication(_terminal.getNetwork().getNrOfCommunications(),
                _terminal.getNetwork().getTerminal(_terminal.getKey()), _terminal.getNetwork().getTerminal(t),true));
        _terminal.getNetwork().incrementCommunicationNr();
    }

    @Override
    void acceptVoiceCall() throws UnavailableTerminalException {
        //Do nothing
        throw new UnavailableTerminalException(_terminal.getKey(), toString());
    }

    @Override
    void makeVideoCall(String t) throws InexistentKeyException, UnavailableTerminalException, NoVideoSupportException {
        _terminal.getNetwork().getTerminal(t).acceptVideoCall();
        _terminal.setCurrentCommunication(new VideoCommunication(_terminal.getNetwork().getNrOfCommunications(),
                _terminal.getNetwork().getTerminal(_terminal.getKey()), _terminal.getNetwork().getTerminal(t),true));
        _terminal.getNetwork().incrementCommunicationNr();
    }

    @Override
    void acceptVideoCall() throws UnavailableTerminalException {
        throw new UnavailableTerminalException(_terminal.getKey(), toString());
    }

    @Override
    void notifyClients(String s) {
        var network = _terminal.getNetwork();
        for(String key: _terminal.getClientsToNotify()) {
            network.notifyClient(key, new SilentToIdleNotification(_terminal.getKey()));
        }
    }
}
