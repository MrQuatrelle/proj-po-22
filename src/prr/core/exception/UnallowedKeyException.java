package prr.core.exception;

import java.io.Serial;

public class UnallowedKeyException extends Exception {

    @Serial
    private static final long serialVersionUID = 202213101647L;

    private static final String MESSAGE = "[CORE] Chave não permitida: ";

    public UnallowedKeyException(String key) {
        super(MESSAGE + key);
    }
}
