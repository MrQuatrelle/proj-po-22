package prr.core;

import prr.core.exception.InexistentKeyException;
import prr.core.exception.NoVideoSupportException;
import prr.core.exception.UnavailableTerminalException;

public class FancyTerminal extends Terminal {

    FancyTerminal(String key, String clientKey, Network network) {
        super(key, clientKey, network);
        _type = "FANCY";
    }

    @Override
    public void makeVideoCall(String t) throws InexistentKeyException, UnavailableTerminalException, NoVideoSupportException {
        _state.makeVideoCall(t);
        this.setStatus("BUSY");
    }

    @Override
    public void acceptVideoCall() throws UnavailableTerminalException {
        _state.acceptVideoCall();
        this.setStatus("BUSY");
    }
}
