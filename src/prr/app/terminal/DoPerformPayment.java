package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.InexistentPaymentException;
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
        try {
            if (_receiver.canPerformPayment(comId,_receiver))
                    _receiver.performPayment(comId);
            else
                _display.popup(Message.invalidCommunication());
        } catch (InexistentPaymentException e) {
            _display.popup(Message.invalidCommunication());
        }
    }
}
