package prr.core.exception;

import java.io.Serial;

public class InexistentKeyException extends Exception {

    private static final String MESSAGE = "[CORE] Chave não existe: ";
    private final String _key;

    public InexistentKeyException(String key) {
        super(MESSAGE + key);
        _key = key;
    }

    public String getKey() {
        return _key;
    }
}

