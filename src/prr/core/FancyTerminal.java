package prr.core;

public class FancyTerminal extends Terminal {


    public FancyTerminal(String key, String clientKey) {
        super(key, clientKey);
    }

    @Override
    public void makeVideoCall(Terminal receiver) {
        //TODO
        this.setStatus(TerminalStatus.BUSY);
    }

    @Override
    public void acceptVideoCall(Terminal caller) {
        //TODO
        this.setStatus(TerminalStatus.BUSY);
    }
}
