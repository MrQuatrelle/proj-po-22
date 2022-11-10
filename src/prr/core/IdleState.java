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
    void makeVoiceCall(String t) throws InexistentKeyException, UnavailableTerminalException {
        if (t.equals(_terminal.getKey()))
            throw new UnavailableTerminalException(t, "BUSY");
        var comm = new VoiceCommunication(_terminal.getNetwork().getNrOfCommunications() + 1,
                _terminal.getNetwork().getTerminal(_terminal.getKey()), _terminal.getNetwork().getTerminal(t),
                true);
        _terminal.getNetwork().getTerminal(t).acceptVoiceCall(comm);
        _terminal.setCurrentCommunication(comm);
        _terminal.getNetwork().addCommunication(comm);
        _terminal.getClient().addComFrom(comm);
        _terminal.setStatus("BUSY");
        _terminal.incrementNrOfCommunications();
    }

    @Override
    void acceptVoiceCall(VoiceCommunication communication) {
        _terminal.setCurrentCommunication(communication);
        _terminal.getClient().addComTo(communication);
        _terminal.setStatus("BUSY");
        communication.getReceiver().incrementNrOfCommunications();
    }

    @Override
    void makeVideoCall(String t) throws InexistentKeyException, UnavailableTerminalException, NoVideoSupportException {
        if (t.equals(_terminal.getKey()))
            throw new UnavailableTerminalException(t, "BUSY");
        var com = new VideoCommunication(_terminal.getNetwork().getNrOfCommunications() + 1,
                _terminal.getNetwork().getTerminal(_terminal.getKey()), _terminal.getNetwork().getTerminal(t),
                true);
        _terminal.getNetwork().getTerminal(t).acceptVideoCall(com);
        _terminal.setCurrentCommunication(com);
        _terminal.getNetwork().addCommunication(com);
        _terminal.getClient().addComFrom(com);
        _terminal.setStatus("BUSY");
        _terminal.incrementNrOfCommunications();
    }

    @Override
    void acceptVideoCall(VideoCommunication communication) {
        _terminal.setCurrentCommunication(communication);
        _terminal.getClient().addComTo(communication);
        _terminal.setStatus("BUSY");
        communication.getReceiver().incrementNrOfCommunications();
    }

    @Override
    void notifyClients(String s) {
        //Do nothing
    }

    @Override
    void acceptTextCommunication(TextCommunication communication) {
        _terminal.getClient().addComTo(communication);
        communication.getReceiver().incrementNrOfCommunications();
    }

    @Override
    void makeTextCommunication(String destinationKey, String message) throws InexistentKeyException,
            UnavailableTerminalException {
        var com = new TextCommunication(_terminal.getNetwork().getNrOfCommunications() + 1,
                _terminal, _terminal.getNetwork().getTerminal(destinationKey),false,message);
        com.computeCost( _terminal.getClient().getTypeString(),_terminal);
        var payment = new Payment(_terminal.getNetwork().getNrOfCommunications() + 1,false,
                com.getCost());
        _terminal.getClient().addComFrom(com);
        _terminal.getNetwork().getTerminal(destinationKey).acceptTextCommunication(com);
        _terminal.getNetwork().addCommunication(com);
        _terminal.addPayment(payment);
        _terminal.getClient().addPayment(payment);
        _terminal.incrementNrOfCommunications();
    }
}
