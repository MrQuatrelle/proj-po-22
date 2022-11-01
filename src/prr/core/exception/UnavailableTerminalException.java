package prr.core.exception;

public class UnavailableTerminalException extends Exception {
    private final String _key;
    private final String _state;

    public UnavailableTerminalException(String key, String state) {
        super();
        _key = key;
        _state = state;
    }

    public String getKey() {
        return _key;
    }

    public String getState() {
        return _state;
    }
}
