package prr.core;

import prr.core.exception.InexistentKeyException;

public abstract class InteractiveCommunication extends Communication {

    private int _duration;

    InteractiveCommunication(int id, Terminal sender, Terminal receiver, boolean isOnGoing, String comType) throws InexistentKeyException {
        super(id, sender, receiver, isOnGoing,comType);
        _duration = 0;
    }

    @Override
    int getSize() {
        return _duration;
    }

    void changeDuration(int duration){
        _duration = duration;
    }

    abstract double computeCost(Client.Type type);
}
