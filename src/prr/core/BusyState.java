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
        var comm = _terminal.getCommunication();
        if(comm.getSender() == _terminal) {
            comm.changeDuration(size);
            comm.endCommunication(size);
            comm.computeCost(_terminal.getClient().getTypeString(), _terminal);
            _terminal.getNetwork().addCommunication(comm);
            var p = new Payment(_terminal.getNetwork().getNrOfCommunications(), false, comm.getCost());
            _terminal.addPayment(p);
            _terminal.getClient().addPayment(p);
            comm.getReceiver().endOngoingCommunication(size);
            _terminal.getClient().addComFrom(comm);
        }
        else {
            _terminal.getClient().addComTo(comm);
        }
        _terminal.setCurrentCommunication(null);
        _terminal.setStatus(_previous.toString());
        return comm.getCost();
    }

    @Override
    void makeVoiceCall(String t) {
        //Do nothing
    }

    @Override
    void acceptVoiceCall(VoiceCommunication communication) throws UnavailableTerminalException {
        _terminal.addClientToNotify(communication.getSender().getClientKey());
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
    void acceptTextCommunication(TextCommunication communication) {
        _terminal.getClient().addComTo(communication);
        communication.getReceiver().incrementNrOfCommunications();
    }

    @Override
    void makeTextCommunication(String destinationKey, String message) {
    }
}
