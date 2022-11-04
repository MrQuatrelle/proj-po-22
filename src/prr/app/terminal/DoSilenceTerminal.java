package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;

import java.util.Objects;
//FIXME add more imports if needed

/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {

    DoSilenceTerminal(Network context, Terminal terminal) {
        super(Label.MUTE_TERMINAL, context, terminal);
    }

    @Override
    protected final void execute() throws CommandException {
        if (Objects.equals(_receiver.getStatus(), "SILENCE")){
            _display.add(Message.alreadySilent());
            _display.display();
        }
        _receiver.setStatus("SILENCE");
    }
}
