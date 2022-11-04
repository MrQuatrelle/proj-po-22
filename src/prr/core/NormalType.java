package prr.core;

public class NormalType extends ClientType {

    NormalType(Client c) {
        super(c, ClientTypeEnum.NORMAL);
    }

    @Override
    void checkTypeUpdate(Communication c) {
        if (getClient().getBalanceValue() < 0) {
            getClient().setType(new NormalType(getClient()));
            return;
        }
        if (getClient().getBalanceValue() > 500)
            getClient().setType(new GoldType(getClient()));
    }
}
