package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;

import java.util.Objects;

/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {

    DoTurnOffTerminal(Network context, Terminal terminal) {
        super(Label.POWER_OFF, context, terminal);
    }

    @Override
    protected final void execute() throws CommandException {
        if (Objects.equals(_receiver.getStatus(), "OFF")) {
            _display.add(Message.alreadyOff());
            _display.display();
        }
        if(!Objects.equals(_receiver.getStatus(), "BUSY"))
            _receiver.setStatus("OFF");
    }
}
