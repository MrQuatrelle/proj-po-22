package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;

import java.util.Objects;

/**
 * Turn on the terminal.
 */
class DoTurnOnTerminal extends TerminalCommand {

    DoTurnOnTerminal(Network context, Terminal terminal) {
        super(Label.POWER_ON, context, terminal);
    }

    @Override
    protected final void execute() throws CommandException {
        if (Objects.equals(_receiver.getStatus(), "IDLE") ||
                Objects.equals(_receiver.getStatus(), "BUSY")) {
            _display.add(Message.alreadyOn());
            _display.display();
        }
        _receiver.setStatus("IDLE");
    }
}
