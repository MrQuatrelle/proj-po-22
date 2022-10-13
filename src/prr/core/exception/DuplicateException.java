package prr.core.exception;

import java.io.Serial;

public class DuplicateException extends Exception {
    @Serial
    private static final long serialVersionUID = 202213102226L;

    private static final String MESSAGE = "[CORE] Chave duplicada: ";

    public DuplicateException(String key) {
        super(key);
    }
}
