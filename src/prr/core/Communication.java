package prr.core;

import prr.core.exception.InexistentKeyException;
import prr.core.exception.NoOngoingCommunicationException;

import java.io.Serial;
import java.io.Serializable;

public abstract class Communication implements Serializable {

    @Serial
    private static final long serialVersionUID = 202208091753L;

    private final int _id;

    private boolean _isPaid;

    protected double _cost;

    private boolean _isOnGoing;

    private final Terminal _sender;

    private final Terminal _receiver;

    private final String _clientType;

    private final String _comType;

    Communication (int id, Terminal sender, Terminal receiver, boolean isOnGoing, String comType)
            throws InexistentKeyException {
        _id = id;
        _sender = sender;
        _receiver = receiver;
        _isOnGoing = isOnGoing;
        _clientType = _sender.getNetwork().getClient(_sender.getClientKey()).getTypeString();
        _comType = comType;
    }

    public String toString(){
        var out = new StringBuilder(_comType + "|" + _id + "|" +
                _sender.getKey() + "|" + _receiver.getKey() + "|");
        if (_isOnGoing) out.append(0 + "|" + 0 + "|" + "ONGOING");
        else out.append(getSize() + "|" + Math.round(getCost()) + "|" + "FINISHED");

        return new String(out);
    }
    String getClientType() {
        return  _clientType;
    }

    public int getId() {
        return _id;
    }

    Terminal getReceiver(){
        return _receiver;
    }

    Terminal getSender(){
        return _sender;
    }

    void endCommunication(int size) throws InexistentKeyException, NoOngoingCommunicationException {
        _isOnGoing = false;
    }
     boolean getState(){
        return _isOnGoing;
     }
    abstract void computeCost(String type, Terminal terminal);

    abstract int getSize();

    double getCost() {
        return _cost;
    }

    boolean getIsOngoing(){
        return _isOnGoing;
    }
    String getType() {
        return _comType;
    }
}
