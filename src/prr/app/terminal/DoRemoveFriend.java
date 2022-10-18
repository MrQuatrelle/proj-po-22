package prr.app.terminal;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.InexistentKeyException;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {

    DoRemoveFriend(Network context, Terminal terminal) {
        super(Label.REMOVE_FRIEND, context, terminal);
        addStringField("key",Message.terminalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        var key = stringField("key");
        try {
            _receiver.removeFriend(key);
        } catch (InexistentKeyException e) {
            throw new UnknownTerminalKeyException(e.getKey());
        }
    }
}
