package prr.core;

public class Payment {
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

    void pay(){
        _paid = true;
    }

    int getId() {
        return _communicationId;
    }
}

