package prr.core;

public class FancyTerminal extends Terminal {


    public FancyTerminal(String key, String clientKey, Network network) {
        super(key, clientKey, network);
        _type = "FANCY";
    }

    @Override
    public void makeVideoCall(Terminal receiver) {
        this.setStatus(Status.BUSY);
    }

    @Override
    public void acceptVideoCall(Terminal caller) {
        this.setStatus(Status.BUSY);
    }
}
