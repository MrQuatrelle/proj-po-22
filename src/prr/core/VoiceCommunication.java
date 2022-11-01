package prr.core;

public class VoiceCommunication extends InteractiveCommunication {

    VoiceCommunication(int id, Terminal sender, Terminal receiver, boolean isOnGoing, int duration){
        super(id, sender, receiver, isOnGoing,duration);
    }
    @Override
    double computeCost() {
        return 0;
    }
}
