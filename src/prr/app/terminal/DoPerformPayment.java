package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
// Add more imports if needed

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

    DoPerformPayment(Network context, Terminal terminal) {
        super(Label.PERFORM_PAYMENT, context, terminal);
        addIntegerField("comId", Message.commKey());
    }

    @Override
    protected final void execute() throws CommandException {
        var comId = integerField("comId");
        if (_receiver.canPerformPayment(comId,_receiver)){
            _receiver.performPayment(comId);
        }
        else {
            _display.add(Message.invalidCommunication());
            _display.display();
        }
    }
}
