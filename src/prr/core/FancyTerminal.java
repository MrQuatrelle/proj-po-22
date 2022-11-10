package prr.core;

import prr.core.exception.InexistentKeyException;
import prr.core.exception.NoVideoSupportException;
import prr.core.exception.UnavailableTerminalException;

public class FancyTerminal extends Terminal {

    FancyTerminal(String key, Client client, Network network) {
        super(key, client, network);
         super.setType("FANCY");
    }

    @Override
    public void makeVideoCall(String t) throws InexistentKeyException,
            UnavailableTerminalException, NoVideoSupportException {
        getState().makeVideoCall(t);

    }

    @Override
    public void acceptVideoCall(VideoCommunication communication) throws UnavailableTerminalException {
        getState().acceptVideoCall(communication);
    }
}
