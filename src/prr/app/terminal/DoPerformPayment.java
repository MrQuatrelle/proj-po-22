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
            if (_receiver.canPerformPayment(comId,_receiver)){
                try {
                    _receiver.performPayment(comId);
                } catch (InexistentPaymentException e) {
                    _display.popup(Message.invalidCommunication());
                }
            }
            else {
                _display.add(Message.invalidCommunication());
                _display.display();
            }
        } catch (InexistentPaymentException e) {
            _display.popup(Message.invalidCommunication());
        }
    }
}
