package prr.core.exception;

import java.io.Serial;

public class InvalidStatusException extends Exception {

    private static final String MESSAGE = "[CORE] Status de terminal não permitida: ";

    public InvalidStatusException(String status) {
        super(status);
    }
}
