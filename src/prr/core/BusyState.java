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
        return _terminal.getCommunication().getReceiver() == _terminal;
    }

    @Override
    boolean canStartCommunication() {
        return false;
    }

    @Override
    double endOngoingCommunication(int size) throws InexistentKeyException {
        _terminal.getCommunication().changeDuration(size);
        _terminal.getCommunication().endCommunication();
        _terminal.addPayment(new Payment(_terminal.getNetwork().getNrOfCommunications(),false,
                _terminal.getCommunication().computeCost( _terminal.getClient().getType())));
        _terminal.getClient().addComFrom(new VoiceCommunication(_terminal.getNetwork().getNrOfCommunications(),
                _terminal, _terminal.getCommunication().getReceiver(),false));
        _terminal.getCommunication().getReceiver().getClient().addComTo(new VoiceCommunication(_terminal.getNetwork().getNrOfCommunications(),
                _terminal, _terminal.getCommunication().getReceiver(),false));
        _terminal.getNetwork().addCommunication(new VoiceCommunication(_terminal.getNetwork().getNrOfCommunications(),
                _terminal, _terminal.getCommunication().getReceiver(),false));
        return _terminal.getCommunication().computeCost( _terminal.getClient().getType());
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

    @Override
    void notifyClients(String s) {
        var network = _terminal.getNetwork();
        for (String key: _terminal.getClientsToNotify()) {
            network.notifyClient(key, new BusyToIdleNotification(_terminal.getKey()));
        }
    }
}
