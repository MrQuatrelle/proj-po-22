package prr.core;

import java.io.Serial;
import java.io.Serializable;

public class Payment implements Serializable {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202208091753L;
    private final int _communicationId;

    private boolean _paid;

    private final double _cost;

    Payment(int communicationId, boolean paid,double cost) {
        _communicationId = communicationId;
        _paid = paid;
        _cost = cost;
    }

    double getCost(){
        return _cost;
    }

    boolean isPaid(){
        return _paid;
    }

    void pay() {
        _paid = true;
    }

    int getId() {
        return _communicationId;
    }
}

