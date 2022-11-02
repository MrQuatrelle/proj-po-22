package prr.core;

public class Payment {
    private int _communicationId;

    private Client.Type _clientType;

    private boolean _paid;

    private double _cost;

    Payment(int communicationId, boolean paid,double cost) {
        _communicationId = communicationId;
        _paid = paid;
        _cost = cost;
    }



}

