package listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Egor on 05.11.2016.
 */

public class LogListener implements ServletRequestListener {
    private static Logger log = Logger.getLogger("LogListener");

    public LogListener() {
        info("Log listener was created.");
    }

    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest)servletRequestEvent.getServletRequest();
        info("Request from " + request.getServerName() + " was destroyed.");
    }

    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest)servletRequestEvent.getServletRequest();
        info("Request from " + request.getServerName() + " was initialised.");
    }

    private void info(String event) {
        log.log(Level.INFO, event);
    }
}