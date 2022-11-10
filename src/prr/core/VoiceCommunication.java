package prr.core;

import prr.core.exception.InexistentKeyException;

public class VoiceCommunication extends InteractiveCommunication {

    VoiceCommunication(int id, Terminal sender, Terminal receiver, boolean isOnGoing) throws InexistentKeyException {
        super(id, sender, receiver, isOnGoing,"VOICE");
    }
    @Override
    void computeCost(String type, Terminal terminal) {
        switch(type){
            case "NORMAL" -> _cost = getSize() * 20;
            case "GOLD" , "PLATINUM" -> _cost = getSize() * 10;
        }
        if (getSender().hasFriend(getReceiver().getKey()) && getSender() == terminal) {
            _cost = _cost / 2;
        }
    }
}

