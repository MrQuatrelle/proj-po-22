package prr.app.exception;

import pt.tecnico.uilib.menus.CommandException;

/**
 * Exception for unknown clients.
 */
public class UnknownClientKeyException extends CommandException {

    /** @param key Unknown client to report. */
    public UnknownClientKeyException(String key) {
        super(Message.unknownClientKey(key));
    }

}
