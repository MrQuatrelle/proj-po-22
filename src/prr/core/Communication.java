package prr.core;

import prr.core.exception.InexistentKeyException;

import java.io.Serial;
import java.io.Serializable;

public abstract class Communication implements Serializable {

    @Serial
    private static final long serialVersionUID = 202208091753L;

    private int _id;

    private boolean _isPaid;

    protected long _cost;

    protected boolean _isOnGoing;

    private Terminal _sender;

    private Terminal _receiver;

    private Client.Type _clientType;

    Communication (int id, Terminal sender, Terminal receiver, boolean isOnGoing) throws InexistentKeyException {
        _id = id;
        _sender = sender;
        _receiver = receiver;
        _isOnGoing  =isOnGoing;
        _clientType = _sender.getNetwork().getClient(_sender.getClientKey()).getType();
    }

    Client.Type getClientType() {
        return  _clientType;
    }

    abstract double computeCost(Client.Type type);

    abstract int getSize();
}
