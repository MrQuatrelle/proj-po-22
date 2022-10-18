package prr.app.terminal;

import prr.app.exception.UnknownClientKeyException;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.InexistentKeyException;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {

    DoAddFriend(Network context, Terminal terminal) {
        super(Label.ADD_FRIEND, context, terminal);
        addStringField("key",Message.terminalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        var key = stringField("key");
        try {
            _receiver.addFriend(key);
        } catch (InexistentKeyException e) {
            throw new UnknownTerminalKeyException(e.getKey());
        }
    }
}
