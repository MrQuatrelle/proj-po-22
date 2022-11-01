package prr.core;

public class TextCommunication extends Communication {

    private String _message;


    TextCommunication(int id, Terminal sender, Terminal receiver, boolean isOnGoing, String message){
        super(id, sender, receiver, isOnGoing);
        _message = message;
    }

    @Override
    int getSize() {
        return  _message.length();
    }

    @Override
    double computeCost() {
        return 0;
    }
}
