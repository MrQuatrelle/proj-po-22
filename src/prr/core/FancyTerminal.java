package prr.core;

public class FancyTerminal extends Terminal {


    public FancyTerminal(String key, String clientKey, Network network) {
        super(key, clientKey, network);
        _type = "FANCY";
    }

    public void makeVideoCall(Terminal receiver) {
        this.setStatus(Status.BUSY);
    }

    public void acceptVideoCall(Terminal caller) {
        this.setStatus(Status.BUSY);
    }

    public void makeVoiceCall() {
        if(this.canStartCommunication()) {
            _status = Status.BUSY;
        }
    }

    public void acceptVoiceCall() {
        // TODO
        _status = Status.BUSY;
    }

    public void endOngoingCommunication(int size) {
        //TODO
        _status = Status.IDLE;
    }
}
