package controller;

import connectionPool.ConnectionPool;
import dao.AccountDao;
import dao.AuthDataDao;
import exception.*;
import model.Account;
import model.AuthData;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Egor on 04.11.2016.
 */

public class SignInController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String AUTH_DATA_ATTRIBUTE = "auth_data_attribute";
    private static final String AUTH_ERROR_ATTRIBUTE = "auth_error";
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
                messages.put(key, "Please enter " + key);
            }
        }
        if (messages.size() == 0) {
            if (authorizeInDb(request, data)) {
                request.getRequestDispatcher("/clinicalRecordsTableController").forward(request, response);
                return;
            } else {
                messages.put("signIn", "Authorization failed!");
                response.sendRedirect("/signIn");
                return;
            }
        }
        request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        response.sendRedirect("/signIn");
    }

    private boolean authorizeInDb(HttpServletRequest request, Map<String, String> data) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            AuthDataDao authDataDao = new AuthDataDao(connection);
            AuthData authData = authDataDao.authorize(data.get("username"), data.get("password"));
            request.getSession().setAttribute(AUTH_DATA_ATTRIBUTE, authData);
            request.getSession().setAttribute("username", data.get("username"));
        } catch (SQLException | ConnectionPoolException e) {
            ExceptionLogger.connectionException("AuthorizeInDb", e);
            request.getSession().setAttribute(AUTH_ERROR_ATTRIBUTE, "Service is temporarily not available, try later");
            return false;
        } catch (UsernameException e) {
            ExceptionLogger.usernameException("AuthorizeInDb", e);
            request.getSession().setAttribute(AUTH_ERROR_ATTRIBUTE, e.getLocalizedMessage());
            return false;
        } catch (PasswordException e) {
            ExceptionLogger.passwordException("AuthorizeInDb", e);
            request.getSession().setAttribute(AUTH_ERROR_ATTRIBUTE, e.getLocalizedMessage());
            return false;
        }
        return true;
    }
}
