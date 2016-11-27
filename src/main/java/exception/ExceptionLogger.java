package exception;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Created by Egor on 08.11.2016.
 */
public class ExceptionLogger {
    public static final Logger LOGGER = Logger.getLogger(ExceptionLogger.class);

    public static void rollBackException(String s, Exception e) {
        LOGGER.log(Level.ERROR, String.format("RollBackException caught in: %s - %s", s, e.toString()));
    }

    public static void connectionException(String s, Exception e) {
        LOGGER.log(Level.ERROR, String.format("ConnectionException caught in: %s - %s", s, e.toString()));
    }

    public static void usernameException(String s, Exception e) {
        LOGGER.log(Level.ERROR, String.format("UsernameException caught in: %s - %s", s, e.toString()));
    }

    public static void passwordException(String s, Exception e) {
        LOGGER.log(Level.ERROR, String.format("PasswordException caught in: %s - %s", s, e.toString()));
    }

    public static void log(String s) {
        LOGGER.log(Level.ERROR, s);
    }
}
