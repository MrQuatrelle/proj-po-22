package prr.app.exception;

import pt.tecnico.uilib.menus.CommandException;

/** Exception for reporting general problems opening and processing files. */
public class FileOpenFailedException extends CommandException {

    /** @param e the cause of this exception. */
    public FileOpenFailedException(Exception e) {
        super(Message.problemOpeningFile(e), e);
    }

}
