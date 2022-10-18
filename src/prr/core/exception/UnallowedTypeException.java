package prr.core.exception;

import java.io.Serial;

public class UnallowedTypeException extends Exception {

    private static final String MESSAGE = "[CORE] Chave não permitida: ";

    public UnallowedTypeException(String type) {
        super(type);
    }
}
