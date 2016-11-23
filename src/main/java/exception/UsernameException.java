package exception;

/**
 * Created by Egor on 08.11.2016.
 */
public class UsernameException extends Exception {
    public UsernameException() {
        super();
    }

    public UsernameException(String message) {
        super(message);
    }

    public UsernameException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameException(Throwable cause) {
        super(cause);
    }

    protected UsernameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
