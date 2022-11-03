package prr.core.exception;

public class InexistentPaymentException extends Exception {
    private final int _commId;

    public InexistentPaymentException(int id) {
        super();
        _commId = id;
    }

    public int getCommId() {
        return _commId;
    }
}
