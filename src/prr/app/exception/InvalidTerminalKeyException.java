package prr.app.exception;

import pt.tecnico.uilib.menus.CommandException;

/** Exception for unknown terminals. */
public class InvalidTerminalKeyException extends CommandException {

    /** @param key Unknown terminal to report. */
    public InvalidTerminalKeyException(String key) {
        super(Message.invalidTerminalKey(key));
    }

}
