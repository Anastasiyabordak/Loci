package loci.exception;

/**
 * this class contains custom exception entity.
 *
 * @author Milena Vysotskaya
 * @version 1.0
 * @since 2018-11-08
 */
public class CustomException extends Exception {
    /**
     * public default constructor.
     */
    public CustomException() {
    }

    /**
     * constructor with parameters.
     * @param message contains exception message.
     */
    public CustomException(final String message) {
        super(message);
    }

    /**
     * constructor with parameters.
     * @param message contains exception message.
     * @param cause   contains exception reason.
     */
    public CustomException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor with parameters.
     * @param cause contains exception reason.
     */
    public CustomException(final Throwable cause) {
        super(cause);
    }
}
