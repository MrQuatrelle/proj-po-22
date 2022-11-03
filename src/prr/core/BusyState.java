package prr.core;

import prr.core.exception.InexistentKeyException;
import prr.core.exception.NoOngoingCommunicationException;
import prr.core.exception.UnavailableTerminalException;
import prr.core.notification.BusyToIdleNotification;

public class BusyState extends TerminalState {

    private final TerminalState _previous;
    BusyState(Terminal t, TerminalState p) {
        super(t);
        _previous = p;
    }

    @Override
    public String toString() {
        return "BUSY";
    }

    @Override
    boolean canEndCurrentCommunication() {
        try {
            return _terminal.getCommunication().getSender() == _terminal;
        } catch (NoOngoingCommunicationException e) {
            return false;
        }
    }

    @Override
    boolean canStartCommunication() {
        return false;
    }

    @Override
    double endOngoingCommunication(int size) throws InexistentKeyException, NoOngoingCommunicationException {
        _terminal.getCommunication().changeDuration(size);
        if(_terminal.getCommunication().getSender() == _terminal) {
            _terminal.getCommunication().endCommunication(size);
            _terminal.addPayment(new Payment(_terminal.getNetwork().getNrOfCommunications(), false,
                    _terminal.getCommunication().computeCost(_terminal.getClient().getType())));
        }
        _terminal.getClient().addComFrom(_terminal.getCommunication());
        _terminal.getCommunication().getReceiver().getClient().addComTo(_terminal.getCommunication());
        _terminal.getNetwork().addCommunication(_terminal.getCommunication());
        var out = _terminal.getCommunication().computeCost( _terminal.getClient().getType());
        _terminal.setCurrentCommunication(null);
        _terminal.setStatus(_previous.toString());
        return out;
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
