package prr.app.client;

import prr.core.Client;
import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

    DoShowClient(Network receiver) {
        super(Label.SHOW_CLIENT, receiver);
        addStringField("key", Message.key());
    }

    @Override
    protected final void execute() throws CommandException {
        var key = stringField("key");
        Client c = _receiver.getClient(key);
        if (c != null) {
            _display.add(c);
        }
        //TODO: Falta implementar as notificações. Quando isso estiver feito,
        //      adicionar a listagem de notificações aqui
        _display.display();
    }
}
