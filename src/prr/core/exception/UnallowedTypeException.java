package prr.core.exception;

import java.io.Serial;

public class UnallowedTypeException extends Exception {

    @Serial
    private static final long serialVersionUID = 202213101647L;

    private static final String MESSAGE = "[CORE] Chave não permitida: ";

    public UnallowedTypeException(String type) {
        super(type);
    }
}
