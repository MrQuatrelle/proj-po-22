package prr.core;

public class GoldType extends ClientType {
    private int _counter;
    GoldType(Client c) {
        super(c, ClientTypeEnum.GOLD);
    }

    @Override
    void checkTypeUpdate(Communication c) {
        if (c.getType().equals("VIDEO"))
            _counter++;
        else
            _counter = 0;
        if (getClient().getBalanceValue() < 0) {
            getClient().setType(new NormalType(getClient()));
            return;
        }
        if (_counter == 5) {
            getClient().setType(new PlatinumType(getClient()));
        }
    }
}
