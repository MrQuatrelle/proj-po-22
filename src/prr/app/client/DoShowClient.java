package prr.app.client;

import prr.core.Client;
import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import prr.core.exception.InexistentKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

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
        Client s;
        try {
            s = _receiver.getClient(key);
        } catch (InexistentKeyException e) {
            throw new UnknownClientKeyException(e.getKey());
        }
        if (s != null) {
            _display.add(s);
        }
        _display.display();
    }
}
