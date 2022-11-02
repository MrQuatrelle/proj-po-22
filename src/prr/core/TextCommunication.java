package prr.core;

import prr.core.exception.InexistentKeyException;

public class TextCommunication extends Communication {

    private String _message;


    TextCommunication(int id, Terminal sender, Terminal receiver, boolean isOnGoing, String message) throws InexistentKeyException {
        super(id, sender, receiver, isOnGoing);
        _message = message;
    }

    @Override
    int getSize() {
        return  _message.length();
    }

    @Override
    double computeCost(Client.Type type) {
        return 0;
    }
}
