package controller;

import connectionPool.ConnectionPool;
import dao.AuthDataDao;
import exception.*;
import model.AuthData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Egor on 04.11.2016.
 */

@WebServlet("/signInController")
public class SignInController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_sign_in";

    public SignInController() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Set<String> keys = new HashSet<>(Arrays.asList("username", "password"));
        Map<String, String> messages = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        for (String key : keys) {
            String value = request.getParameter(key);
            if (value != null && !value.isEmpty()) {
                data.put(key, value);
            } else {
                messages.put(key, "signIn.message.enter." + key);
            }
        }
        if (messages.size() == 0) {
            if (authorizeInDb(request, messages, data)) {
                //TODO: CONTROLLER
                response.sendRedirect("./clinicalRecordsTableController");
            } else {
                response.sendRedirect("./signIn");
            }
            request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
            return;
        }
        request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        request.getRequestDispatcher("./signIn").forward(request, response);
    }

    /**
     *
     * @param request - {@link HttpServletRequest}
     * @param messages - map of messages for output
     * @param data - map of parameters for method
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private boolean authorizeInDb(HttpServletRequest request, Map<String, String> messages, Map<String, String> data)
            throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            AuthDataDao authDataDao = new AuthDataDao(connection);
            AuthData authData = authDataDao.authorize(data.get("username"), data.get("password"));
            request.getSession().setAttribute("username", authData.getUsername());
            request.getSession().setAttribute("fullName", authDataDao.getFullNameByUsername(authData.getUsername()));
            return true;
        } catch (SomeException | SQLException | ConnectionPoolException e) {
            ExceptionLogger.connectionException("AuthorizeInDb", e);
            messages.put("signIn", "signIn.message.fail.connection");
            return false;
        } catch (UsernameException e) {
            ExceptionLogger.usernameException("AuthorizeInDb", e);
            messages.put("signIn", "signIn.message.wrong.authData");
            return false;
        } catch (PasswordException e) {
            ExceptionLogger.passwordException("AuthorizeInDb", e);
            messages.put("signIn", "signIn.message.wrong.authData");
            return false;
        }
    }
}
