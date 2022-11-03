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
        var comm = new VoiceCommunication(_terminal.getNetwork().getNrOfCommunications(),
                _terminal.getNetwork().getTerminal(_terminal.getKey()), _terminal.getNetwork().getTerminal(t),true);
        _terminal.getNetwork().getTerminal(t).acceptVoiceCall(comm);
        _terminal.setCurrentCommunication(comm);
        _terminal.getNetwork().incrementCommunicationNr();
        _terminal.getClient().addComFrom(comm);
    }

    @Override
    void acceptVoiceCall(VoiceCommunication communication) {
        _terminal.setCurrentCommunication(communication);
        _terminal.setStatus("BUSY");
        _terminal.getClient().addComTo(communication);
    }

    @Override
    void makeVideoCall(String t) throws InexistentKeyException, UnavailableTerminalException, NoVideoSupportException {
        var com = new VideoCommunication(_terminal.getNetwork().getNrOfCommunications(),
                _terminal.getNetwork().getTerminal(_terminal.getKey()), _terminal.getNetwork().getTerminal(t),true);
        _terminal.getNetwork().incrementCommunicationNr();
        _terminal.getNetwork().getTerminal(t).acceptVideoCall(com);
        _terminal.setCurrentCommunication(com);
        _terminal.getNetwork().addCommunication(com);
        _terminal.getClient().addComFrom(com);
    }

    @Override
    void acceptVideoCall(VideoCommunication communication) {
        _terminal.setStatus("BUSY");
        _terminal.getClient().addComTo(communication);
    }

    @Override
    void notifyClients(String s) {
        //Do nothing
    }

    @Override
    void acceptTextCommunication(TextCommunication communication) throws UnavailableTerminalException {
        _terminal.getClient().addComTo(communication);
    }

    void makeTextCommunication(String destinationKey, String message) throws InexistentKeyException, UnavailableTerminalException {
        var com = new TextCommunication(_terminal.getNetwork().getNrOfCommunications(),
                _terminal.getNetwork().getTerminal(_terminal.getKey()), _terminal.getNetwork().getTerminal(destinationKey),true,message);
        _terminal.getNetwork().incrementCommunicationNr();
        _terminal.getNetwork().getTerminal(destinationKey).acceptTextCommunication(com);
        _terminal.getNetwork().addCommunication(com);
    }
}
