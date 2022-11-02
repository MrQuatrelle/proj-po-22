package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.InexistentKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

    DoEndInteractiveCommunication(Network context, Terminal terminal) {
        super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canEndCurrentCommunication());
        addIntegerField("duration",Message.duration());
    }

    @Override
    protected final void execute() throws CommandException {
        var duration = integerField("duration");
        if (_receiver.canEndCurrentCommunication()){
            try {
                _receiver.endOngoingCommunication(duration);
            } catch (InexistentKeyException e) {
            }
        }
        try {
            _display.add(Message.communicationCost(Math.round(_receiver.endOngoingCommunication(duration))));
        } catch (InexistentKeyException e) {
        }
    }
}
