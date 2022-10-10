package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

    DoShowClientPaymentsAndDebts(Network receiver) {
        super(Label.SHOW_CLIENT_BALANCE, receiver);
        addStringField("key", Message.key());
    }

    @Override
    protected final void execute() throws CommandException {
        var key = stringField("key");
        var p = _receiver.getClientPaymentValue(key);
        var d = _receiver.getDebtPaymentValue(key);
        if (p >= 0 && d >= 0)
            _display.add(Message.clientPaymentsAndDebts(key, p, d));
        _display.display();
    }
}
