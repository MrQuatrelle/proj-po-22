package prr.app.terminals;

import prr.app.exception.InvalidTerminalKeyException;
import prr.app.terminal.Menu;
import prr.core.Network;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.exception.InexistentKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add mode import if needed

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

    DoOpenMenuTerminalConsole(Network receiver) {
        super(Label.OPEN_MENU_TERMINAL, receiver);
        addStringField("key", Message.terminalKey());
    }

    //check
    @Override
    protected final void execute() throws CommandException, UnknownTerminalKeyException {
        var key = stringField("key");
        Menu menu = null;
        try {
            menu = new Menu(_receiver, _receiver.getTerminal(key) );
        } catch (InexistentKeyException e) {
            throw new UnknownTerminalKeyException(e.getKey());
        }
        menu.open();
    }
}
