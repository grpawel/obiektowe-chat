package agh.cs.lab.names;

/**
 * Created by Paweł Grochola on 23.01.2017.
 */
public class NameAlreadyTakenException extends Exception {
    public NameAlreadyTakenException() {
    }

    public NameAlreadyTakenException(String message) {
        super(message);
    }

    public NameAlreadyTakenException(Throwable cause) {
        super(cause);
    }

    public NameAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }
}
