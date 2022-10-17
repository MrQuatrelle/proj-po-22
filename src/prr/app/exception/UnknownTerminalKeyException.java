package prr.app.exception;

import pt.tecnico.uilib.menus.CommandException;

/** Exception for unknown terminals. */
public class UnknownTerminalKeyException extends CommandException {

    /** @param key Unknown terminal to report. */
    public UnknownTerminalKeyException(String key) {
        super(Message.unknownTerminalKey(key));
    }

}
