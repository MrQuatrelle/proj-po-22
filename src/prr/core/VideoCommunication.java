package prr.core;

import prr.core.exception.InexistentKeyException;

public class VideoCommunication extends InteractiveCommunication{

    VideoCommunication(int id, Terminal sender, Terminal receiver, boolean isOnGoing) {
        super(id, sender, receiver, isOnGoing,"VIDEO");
    }
    @Override
    double computeCost(Client.Type type) {
        switch(type){
            case NORMAL -> _cost = getSize() * 30;
            case GOLD -> _cost = getSize() * 20;
            case PLATINUM -> _cost = getSize() * 10;
        }
        return  _cost;
    }
}
