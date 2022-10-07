package prr.core;

import java.util.LinkedList;

public class Client {

    private String _key;
    private String _name;
    private long _ssNum;
    private ClientType _type;
    private LinkedList<Notification> _notifications; //TODO: Implement Notification

    public Client(String key, String name, long ss) {
        _key = key;
        _name = name;
        _ssNum = ss;
        _type = Normal;
    }

}
