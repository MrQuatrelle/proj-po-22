package prr.core.exception;

import java.io.Serial;

public class UnallowedKeyException extends Exception {

    private String _key;

    private static final String MESSAGE = "[CORE] Chave não permitida: ";

    public UnallowedKeyException(String key) {
        super(MESSAGE + key);
        _key = key;
    }

    public String getKey() {
        return _key;
    }
}
