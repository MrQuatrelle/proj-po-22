package prr.app.exception;

import pt.tecnico.uilib.menus.CommandException;

/** Exception thrown when a client key is duplicated. */
public class DuplicateClientKeyException extends CommandException {

    /** @param key the duplicated key */
    public DuplicateClientKeyException(String key) {
        super(Message.duplicateClientKey(key));
    }

}
