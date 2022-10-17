package prr.core.exception;

/**
 *
 */
public class UnavailableFileException extends Exception {

    /** The requested filename. */
    String _filename;

    /**
     * @param filename
     */
    public UnavailableFileException(String filename) {
        super("Erro a processar ficheiro " + filename);
        _filename = filename;
    }

    /**
     * @return the requested filename
     */
    public String getFilename() {
        return _filename;
    }
}
