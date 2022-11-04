package prr.core;

public class PlatinumType extends ClientType {
    private int _counter;
    PlatinumType(Client c) {
        super(c, ClientTypeEnum.PLATINUM);
    }

    @Override
    void checkTypeUpdate(Communication c) {
        if (c.getType().equals("TEXT"))
            _counter++;
        else
            _counter = 0;
        if (getClient().getBalanceValue() < 0) {
            getClient().setType(new NormalType(getClient()));
            return;
        }
        if(_counter == 2) {
            getClient().setType(new GoldType(getClient()));
        }
    }
}
