package prr.core;

import prr.core.exception.InexistentKeyException;

public abstract class InteractiveCommunication extends Communication {

    private int _duration;

    InteractiveCommunication(int id, Terminal sender, Terminal receiver, boolean isOnGoing, int duration) throws InexistentKeyException {
        super(id, sender, receiver, isOnGoing);
        _duration = duration;
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
