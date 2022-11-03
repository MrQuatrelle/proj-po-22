package prr.core;

import prr.core.exception.InexistentKeyException;
import prr.core.exception.UnavailableTerminalException;
import prr.core.notification.BusyToIdleNotification;

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
        return _terminal.getCommunication().getSender() == _terminal;
    }

    @Override
    boolean canStartCommunication() {
        return false;
    }

    @Override
    double endOngoingCommunication(int size) throws InexistentKeyException {
        var com = new VoiceCommunication(_terminal.getNetwork().getNrOfCommunications(),
                _terminal, _terminal.getCommunication().getReceiver(),false);
        _terminal.getCommunication().changeDuration(size);
        _terminal.getCommunication().endCommunication();
        _terminal.addPayment(new Payment(_terminal.getNetwork().getNrOfCommunications(),false,
                _terminal.getCommunication().computeCost( _terminal.getClient().getType())));
        _terminal.getClient().addComFrom(com);
        _terminal.getCommunication().getReceiver().getClient().addComTo(com);
        _terminal.getNetwork().addCommunication(com);
        _terminal.setCurrentCommunication(null);
        return _terminal.getCommunication().computeCost( _terminal.getClient().getType());
    }

    @Override
    void makeVoiceCall(String t) {
        //Do nothing
    }

    @Override
    void acceptVoiceCall(VoiceCommunication communication) throws UnavailableTerminalException {
        //Do nothing
        throw new UnavailableTerminalException(_terminal.getKey(), toString());
    }

    @Override
    void makeVideoCall(String t) {
        //Do nothing
    }

    @Override
    void acceptVideoCall(VideoCommunication communication) throws UnavailableTerminalException {
        throw new UnavailableTerminalException(_terminal.getKey(), toString());
        //Do nothing
    }

    @Override
    void notifyClients(String s) {
        var network = _terminal.getNetwork();
        for (String key: _terminal.getClientsToNotify()) {
            network.notifyClient(key, new BusyToIdleNotification(_terminal.getKey()));
        }
    }

    @Override
    void acceptTextCommunication(TextCommunication communication) throws UnavailableTerminalException {
        _terminal.getClient().addComTo(communication);
    }

    @Override
    void makeTextCommunication(String destinationKey, String message) throws InexistentKeyException, UnavailableTerminalException {

    }
}
