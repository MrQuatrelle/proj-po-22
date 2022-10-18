package prr.core.exception;

import java.io.Serial;

public class DuplicateException extends Exception {

    private final String _key;

    private static final String MESSAGE = "[CORE] Chave duplicada: ";

    public DuplicateException(String key) {
        super(key);
        _key = key;
    }

    public String getKey() {
        return _key;
    }
}
