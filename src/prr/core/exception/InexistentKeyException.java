package prr.core.exception;

import java.io.Serial;

public class InexistentKeyException extends Exception {
    @Serial
    private static final long serialVersionUID = 202213102236L;

    private static final String MESSAGE = "[CORE] Chave não existe: ";

    public InexistentKeyException(String key) {
        super(MESSAGE + key);
    }
}
