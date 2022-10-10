package prr.app.client;

import prr.core.Client;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show all clients.
 */
class DoShowAllClients extends Command<Network> {

    DoShowAllClients(Network receiver) {
        super(Label.SHOW_ALL_CLIENTS, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        _display.addAll(_receiver.getAllClientStrings());
        _display.display();
        //FIXME implement command
    }
}
