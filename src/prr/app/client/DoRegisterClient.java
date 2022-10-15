package prr.app.client;

import prr.core.Network;
import prr.app.exception.DuplicateClientKeyException;
import prr.core.exception.DuplicateException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

    DoRegisterClient(Network receiver) {
        super(Label.REGISTER_CLIENT, receiver);
        addStringField("key", Message.key());
        addStringField("name", Message.name());
        addIntegerField("ss", Message.taxId());
        //FIXME add command fields
    }

    @Override
    protected final void execute() throws CommandException {
        var key = stringField("key");
        var name = stringField("name");
        var ss = integerField("ss");

        try {
            _receiver.addClient(key, name, ss);
        } catch (DuplicateException e) {
            throw new RuntimeException(e);
        }
    }
}
