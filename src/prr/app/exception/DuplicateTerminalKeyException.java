package prr.app.exception;

import pt.tecnico.uilib.menus.CommandException;

/** Exception thrown when a terminal key is duplicated. */
public class DuplicateTerminalKeyException extends CommandException {

    /** @param key Duplicate terminal to report. */
    public DuplicateTerminalKeyException(String key) {
        super(Message.duplicateTerminalKey(key));
    }

}
