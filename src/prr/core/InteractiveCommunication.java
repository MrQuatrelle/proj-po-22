package prr.core;

public abstract class InteractiveCommunication extends Communication {

    private int _duration;

    InteractiveCommunication(int id, Terminal sender, Terminal receiver, boolean isOnGoing, int duration){
        super(id, sender, receiver, isOnGoing);
        _duration = duration;
    }

    @Override
    int getSize() {
        return _duration;
    }
}
