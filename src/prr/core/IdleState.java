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
        _terminal.getNetwork().incrementCommunicationNr();
        var comm = new VoiceCommunication(_terminal.getNetwork().getNrOfCommunications(),
                _terminal.getNetwork().getTerminal(_terminal.getKey()), _terminal.getNetwork().getTerminal(t),true);
        _terminal.getNetwork().getTerminal(t).acceptVoiceCall(comm);
        _terminal.setCurrentCommunication(comm);
        _terminal.getClient().addComFrom(comm);
        _terminal.setStatus("BUSY");
    }

    @Override
    void acceptVoiceCall(VoiceCommunication communication) {
        _terminal.setCurrentCommunication(communication);
        _terminal.getClient().addComTo(communication);
        _terminal.setStatus("BUSY");
    }

    @Override
    void makeVideoCall(String t) throws InexistentKeyException, UnavailableTerminalException, NoVideoSupportException {
        _terminal.getNetwork().incrementCommunicationNr();
        var com = new VideoCommunication(_terminal.getNetwork().getNrOfCommunications(),
                _terminal.getNetwork().getTerminal(_terminal.getKey()), _terminal.getNetwork().getTerminal(t),true);
        _terminal.getNetwork().getTerminal(t).acceptVideoCall(com);
        _terminal.setCurrentCommunication(com);
        _terminal.getNetwork().addCommunication(com);
        _terminal.getClient().addComFrom(com);
        _terminal.setStatus("BUSY");
    }

    @Override
    void acceptVideoCall(VideoCommunication communication) {
        _terminal.setCurrentCommunication(communication);
        _terminal.getClient().addComTo(communication);
        _terminal.setStatus("BUSY");
    }

    @Override
    void notifyClients(String s) {
        //Do nothing
    }

    @Override
    void acceptTextCommunication(TextCommunication communication) throws UnavailableTerminalException {
        _terminal.getClient().addComTo(communication);
    }

    @Override
    void makeTextCommunication(String destinationKey, String message) throws InexistentKeyException, UnavailableTerminalException {
        _terminal.getNetwork().incrementCommunicationNr();
        var com = new TextCommunication(_terminal.getNetwork().getNrOfCommunications(),
                _terminal, _terminal.getNetwork().getTerminal(destinationKey),true,message);
        com.computeCost( _terminal.getClient().getType());
        var payment = new Payment(_terminal.getNetwork().getNrOfCommunications(),false,
                com.getCost());
        _terminal.getClient().addComFrom(com);
        _terminal.getNetwork().getTerminal(destinationKey).acceptTextCommunication(com);
        _terminal.getNetwork().addCommunication(com);
        _terminal.addPayment(payment);
        _terminal.getClient().addPayment(payment);
    }
}
