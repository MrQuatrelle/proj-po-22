package prr.app.lookup;

import prr.app.exception.DuplicateClientKeyException;
import prr.core.Network;
import prr.core.exception.InexistentKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show communications from a client.
 */
class DoShowCommunicationsFromClient extends Command<Network> {

    DoShowCommunicationsFromClient(Network receiver) {
        super(Label.SHOW_COMMUNICATIONS_FROM_CLIENT, receiver);
        addStringField("clientKey",Message.clientKey());
    }

    @Override
    protected final void execute() throws CommandException {
        var key = stringField("clientKey");
        try {
            _receiver.getClient(key).getAllCommunicationFromStrings();
        } catch (InexistentKeyException e) {
            throw new DuplicateClientKeyException(key);
        }

    }
}
