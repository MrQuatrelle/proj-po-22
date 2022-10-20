package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import prr.core.exception.UnchangedNotificationException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

    DoDisableClientNotifications(Network receiver) {
        super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
        addStringField("key", Message.key());
    }

    @Override
    protected final void execute() throws CommandException {
        var key = stringField("key");
        try {
            _receiver.setClientNotification(key, false);
        } catch (UnchangedNotificationException e) {
            _display.popup(Message.clientNotificationsAlreadyDisabled());
        }
    }
}
