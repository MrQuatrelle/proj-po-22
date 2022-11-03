package prr.app.terminal;

import prr.app.exception.UnknownClientKeyException;
import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.exception.InexistentKeyException;
import prr.core.exception.UnavailableTerminalException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

    DoSendTextCommunication(Network context, Terminal terminal) {
        super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
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
            throw new UnknownClientKeyException(_receiver.getClient().getKey());
        } catch (UnavailableTerminalException e) {
            _display.popup(Message.destinationIsOff(destination));
        }
    }
} 
