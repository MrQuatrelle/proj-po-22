package prr.core;

import prr.core.exception.InexistentKeyException;

public class TextCommunication extends Communication {

    private String _message;


    TextCommunication(int id, Terminal sender, Terminal receiver, boolean isOnGoing, String message) throws InexistentKeyException {
        super(id, sender, receiver, isOnGoing,"TEXT");
        _message = message;
    }

    @Override
    int getSize() {
        return  _message.length();
    }

    double normalCost(){
        if (getSize() < 50){
            return 10;
        }
        if (getSize() > 50 && getSize() <100){
            return 16;
        }
        else {return (2 * getSize());}
    }

    double goldCost(){
        if (getSize() < 50){
            return 10;
        }
        if (getSize() > 50 && getSize() <100){
            return 10;
        }
        else {return (2 * getSize());}
    }

    double platinumCost(){
        if (getSize() < 50){
            return 0;
        }
        return 4;
    }
    @Override
    double computeCost(Client.Type type) {
        switch (type) {
            case NORMAL -> _cost = normalCost();
            case GOLD -> _cost = goldCost();
            case PLATINUM -> _cost = platinumCost();
        }
        if (getSender().hasFriend(getReceiver().getKey())) {
            return _cost / 2;
        }
        else return _cost;
    }
}
