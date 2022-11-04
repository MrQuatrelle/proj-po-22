package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.exception.InexistentKeyException;
import prr.core.exception.UnavailableTerminalException;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

    DoSendTextCommunication(Network context, Terminal terminal) {
        super(Label.SEND_TEXT_COMMUNICATION, context, terminal, Terminal::canStartCommunication);
        addStringField("destination", Message.terminalKey());
        addStringField("message", Message.textMessage());
    }

    @Override
    protected final void execute() throws CommandException {
       var destination = stringField("destination");
       var message = stringField("message");
        try {
            _receiver.makeTextCommunication(destination,message);
        } catch (InexistentKeyException e) {
            throw new UnknownTerminalKeyException(e.getKey());
        } catch (UnavailableTerminalException e) {
            _display.popup(Message.destinationIsOff(destination));
        }
    }
} 
