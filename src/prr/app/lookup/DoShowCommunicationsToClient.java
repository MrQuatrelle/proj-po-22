package prr.app.lookup;

import prr.app.exception.DuplicateClientKeyException;
import prr.core.Network;
import prr.core.exception.InexistentKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show communications to a client.
 */
class DoShowCommunicationsToClient extends Command<Network> {

    DoShowCommunicationsToClient(Network receiver) {
        super(Label.SHOW_COMMUNICATIONS_TO_CLIENT, receiver);
        addStringField("clientKey",Message.clientKey());
    }

    @Override
    protected final void execute() throws CommandException {
        var key = stringField("clientKey");
        try {
            _display.addAll(_receiver.getReceivingCommunicationsOfClient(key));
            _display.display();
        } catch (InexistentKeyException e) {
            throw new DuplicateClientKeyException(key);
        }
    }
}
