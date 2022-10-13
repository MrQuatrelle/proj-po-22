package prr.core.exception;


import java.io.Serial;

public class InvalidTerminalException extends Exception {

    @Serial
    private static final long serialVersionUID = 202213101500L;

    private static final String MESSAGE = "[CORE] Erro ao criar o terminal";

    public InvalidTerminalException() {
        super(MESSAGE);
    }

}
