package prr.core.exception;

import java.io.Serial;

public class InvalidStatusException extends Exception {
    @Serial
    private static final long serialVersionUID = 202213102302L;

    private static final String MESSAGE = "[CORE] Status de terminal não permitida: ";

    public InvalidStatusException(String status) {
        super(status);
    }
}
