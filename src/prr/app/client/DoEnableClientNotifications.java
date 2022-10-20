package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import prr.core.exception.UnchangedNotificationException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

    DoEnableClientNotifications(Network receiver) {
        super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
        addStringField("key", Message.key());
    }

    @Override
    protected final void execute() throws CommandException {
        var key = stringField("key");
        try {
            _receiver.setClientNotification(key, true);
        } catch (UnchangedNotificationException e) {
            _display.popup(Message.clientNotificationsAlreadyEnabled());
        }
    }
}
