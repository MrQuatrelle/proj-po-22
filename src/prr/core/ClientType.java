package prr.core;

import java.io.Serial;
import java.io.Serializable;

public abstract class ClientType implements Serializable {

    @Serial
    private static final long serialVersionUID = 202211041222L;

    enum ClientTypeEnum {
        NORMAL,
        GOLD,
        PLATINUM
    }
    private final Client _client;
    private final ClientTypeEnum _type;

    ClientType(Client c, ClientTypeEnum t) {
        _client = c;
        _type = t;
    }

    Client getClient() {
        return _client;
    }

    @Override
    public String toString() {
        return _type.toString();
    }

     abstract void checkTypeUpdate(Communication c);
}
