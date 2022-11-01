package prr.core;

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

    Communication (int id, Terminal sender, Terminal receiver, boolean isOnGoing){
        _id = id;
        _sender = sender;
        _receiver = receiver;
        _isOnGoing  =isOnGoing;
    }

    abstract double computeCost();

    abstract int getSize();
}
