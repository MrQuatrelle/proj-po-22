package prr.core;

import prr.core.exception.NoVideoSupportException;

public class BasicTerminal extends Terminal{

    public BasicTerminal(String key, Client client, Network network) {
        super(key, client, network);
    }

    @Override
    public void makeVideoCall(String receiver) throws NoVideoSupportException {
        throw new NoVideoSupportException(this.getKey());
    }

    @Override
    public void acceptVideoCall() throws NoVideoSupportException {
        throw new NoVideoSupportException(this.getKey());
    }

}
