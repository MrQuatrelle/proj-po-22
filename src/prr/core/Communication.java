package prr.core;

import prr.core.exception.InexistentKeyException;

import java.io.Serial;
import java.io.Serializable;

public abstract class Communication implements Serializable {

    @Serial
    private static final long serialVersionUID = 202208091753L;

    private int _id;

    private boolean _isPaid;

    protected double _cost;

    protected boolean _isOnGoing;

    private Terminal _sender;

    private Terminal _receiver;

    private Client.Type _clientType;

    private String _comType;

    Communication (int id, Terminal sender, Terminal receiver, boolean isOnGoing, String comType) throws InexistentKeyException {
        _id = id;
        _sender = sender;
        _receiver = receiver;
        _isOnGoing  =isOnGoing;
        _clientType = _sender.getNetwork().getClient(_sender.getClientKey()).getType();
        _comType = comType;
    }

    public String toString(){
        var out = new StringBuilder(_comType + "|" + _sender.getNetwork().getNrOfCommunications() + "|" +
                _sender.getKey() + "|" + _receiver.getKey() + "|");
        if (_isOnGoing) out.append(0 + "|" + 0 + "|" + "ONGOING");
        else out.append(getSize() + "|" + computeCost(_clientType) + "|" + "FINISHED");

        return new String(out);
    }
    Client.Type getClientType() {
        return  _clientType;
    }

    Terminal getReceiver(){
        return _receiver;
    }

    Terminal getSender(){
        return _sender;
    }

    void endCommunication(){
        _isOnGoing = false;
    }
     boolean getState(){
        return _isOnGoing;
     }
    abstract double computeCost(Client.Type type);

    abstract int getSize();
}
