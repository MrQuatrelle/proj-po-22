package prr.core.exception;

public class InexistentCommunicationException extends Exception {

    private static final String MESSAGE = "[CORE] ID não existe: ";
    private final int _id;

    public InexistentCommunicationException(int id) {
        super(MESSAGE + id);
        _id = id;
    }

    public int getId() {
        return _id;
    }
}

