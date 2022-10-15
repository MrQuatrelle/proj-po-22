package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Turn on the terminal.
 */
class DoTurnOnTerminal extends TerminalCommand {

    DoTurnOnTerminal(Network context, Terminal terminal) {
        super(Label.POWER_ON, context, terminal);
    }

    @Override
    protected final void execute() throws CommandException {
        if (_receiver.getStatus() == Terminal.TerminalStatus.IDLE){
            _display.add(Message.alreadyOn());
            _display.display();
        }
        _receiver.setStatus(Terminal.TerminalStatus.IDLE);
    }
}
