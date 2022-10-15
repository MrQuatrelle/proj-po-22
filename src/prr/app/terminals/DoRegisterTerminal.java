package prr.app.terminals;

import prr.core.Network;
import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import prr.core.exception.UnallowedTypeException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.security.InvalidKeyException;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

    DoRegisterTerminal(Network receiver) {
        super(Label.REGISTER_TERMINAL, receiver);
        addStringField("key", Message.terminalKey());
        addOptionField("type", Message.terminalType(), "BASIC", "FANCY");
        addStringField("client", Message.clientKey());
    }

    @Override
    protected final void execute() throws CommandException {
    var key = stringField("key");
    var type = optionField("type");
    var client = stringField("client");
        try {
            _receiver.addTerminal(type,key,client);
        } catch (UnallowedTypeException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
