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
        return true;
    }

    @Override
    double endOngoingCommunication(int size) {
        return 0;
    }

    @Override
    void makeVoiceCall(String t) throws InexistentKeyException, UnavailableTerminalException {
        if (t.equals(_terminal.getKey()))
            throw new UnavailableTerminalException(t, "BUSY");
        var communication = new VoiceCommunication(_terminal.getNetwork().getNrOfCommunications() + 1,
                _terminal.getNetwork().getTerminal(_terminal.getKey()), _terminal.getNetwork().getTerminal(t),true);
        _terminal.getNetwork().getTerminal(t).acceptVoiceCall(communication);
        _terminal.setCurrentCommunication(communication);
        _terminal.setStatus("BUSY");
    }

    @Override
    void acceptVoiceCall(VoiceCommunication communication) throws UnavailableTerminalException {
        _terminal.addClientToNotify(communication.getSender().getClientKey());
        throw new UnavailableTerminalException(_terminal.getKey(), toString());
    }

    @Override
    void makeVideoCall(String t) throws InexistentKeyException, UnavailableTerminalException, NoVideoSupportException {
        if (t.equals(_terminal.getKey()))
            throw new UnavailableTerminalException(t, "BUSY");
        var com = new VideoCommunication(_terminal.getNetwork().getNrOfCommunications() + 1,
                _terminal.getNetwork().getTerminal(_terminal.getKey()), _terminal.getNetwork().getTerminal(t),true);
        _terminal.getNetwork().getTerminal(t).acceptVideoCall(com);
        _terminal.setCurrentCommunication(com);
        _terminal.getNetwork().addCommunication(com);
        _terminal.getClient().addComFrom(com);
        _terminal.setStatus("BUSY");
    }

    @Override
    void acceptVideoCall(VideoCommunication communication) throws UnavailableTerminalException {
        _terminal.addClientToNotify(communication.getSender().getClientKey());
        throw new UnavailableTerminalException(_terminal.getKey(), toString());
    }

    @Override
    void notifyClients(String s) {
        var network = _terminal.getNetwork();
        for(String key: _terminal.getClientsToNotify()) {
            network.notifyClient(key, new SilentToIdleNotification(_terminal.getKey()));
        }
    }

    @Override
    void acceptTextCommunication(TextCommunication communication) {
        _terminal.getClient().addComTo(communication);
    }

    void makeTextCommunication(String destinationKey, String message) throws InexistentKeyException, UnavailableTerminalException {
        var com = new TextCommunication(_terminal.getNetwork().getNrOfCommunications() + 1,
                _terminal.getNetwork().getTerminal(_terminal.getKey()), _terminal.getNetwork().getTerminal(destinationKey),false,message);
        com.computeCost( _terminal.getClient().getTypeString());
        var payment = new Payment(_terminal.getNetwork().getNrOfCommunications() + 1, false,
                com.getCost());
        _terminal.getClient().addComFrom(com);
        _terminal.getNetwork().getTerminal(destinationKey).acceptTextCommunication(com);
        _terminal.getNetwork().addCommunication(com);
        _terminal.addPayment(payment);
        _terminal.getClient().addPayment(payment);
    }
}
