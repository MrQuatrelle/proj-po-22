package prr.core;

import prr.core.exception.InexistentKeyException;
import prr.core.exception.UnavailableTerminalException;
import prr.core.notification.OffToIdleNotification;
import prr.core.notification.OffToSilentNotification;

public class OffState extends TerminalState {
    OffState(Terminal t) {
        super(t);
    }

    @Override
    public String toString() {
        return "OFF";
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
    void makeVoiceCall(String t) {
        //Do nothing
    }

    @Override
    void acceptVoiceCall(VoiceCommunication communication) throws UnavailableTerminalException {
        throw new UnavailableTerminalException(_terminal.getKey(), toString());
    }

    @Override
    void makeVideoCall(String t) {
        //Do nothing
    }

    @Override
    void acceptVideoCall(VideoCommunication communication) throws UnavailableTerminalException {
        throw new UnavailableTerminalException(_terminal.getKey(), toString());
    }

    @Override
    void notifyClients(String s) {
        var network = _terminal.getNetwork();
        for (String key: _terminal.getClientsToNotify()) {
            switch (s) {
                case "SILENCE" -> network.notifyClient(key, new OffToSilentNotification(_terminal.getKey()));
                case "IDLE" -> network.notifyClient(key, new OffToIdleNotification(_terminal.getKey()));
            }
        }
    }

    @Override
    void acceptTextCommunication(TextCommunication communication) throws UnavailableTerminalException {
        throw new UnavailableTerminalException(_terminal.getKey(),"OFF");
    }

    void makeTextCommunication(String destinationKey, String message) throws InexistentKeyException, UnavailableTerminalException {

    }
}
