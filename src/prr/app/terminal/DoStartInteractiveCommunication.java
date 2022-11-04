package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.exception.InexistentKeyException;
import prr.core.exception.NoVideoSupportException;
import prr.core.exception.UnavailableTerminalException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

    DoStartInteractiveCommunication(Network context, Terminal terminal) {
        super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, Terminal::canStartCommunication);
        addStringField("destination", Message.terminalKey());
        addOptionField("type", Message.commType(), "VOICE", "VIDEO");
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            if (optionField("type").equals("VOICE")) {
                _receiver.makeVoiceCall(stringField("destination"));
                return;
            }
            _receiver.makeVideoCall(stringField("destination"));
        } catch (InexistentKeyException e) {
            throw new UnknownTerminalKeyException(stringField("destination"));
        } catch (NoVideoSupportException e) {
            if ((e.getMessage()).equals(_receiver.getKey())){
                _display.popup(Message.unsupportedAtOrigin(e.getMessage(), "VIDEO"));
            }
            else
                _display.popup(Message.unsupportedAtDestination(e.getMessage(), "VIDEO"));
        } catch (UnavailableTerminalException e) {
            switch (e.getState()) {
                case "OFF" -> _display.popup(Message.destinationIsOff(e.getKey()));
                case "BUSY" -> _display.popup(Message.destinationIsBusy(e.getKey()));
                case "SILENCE" -> _display.popup(Message.destinationIsSilent(e.getKey()));
            }
        }
    }
}
