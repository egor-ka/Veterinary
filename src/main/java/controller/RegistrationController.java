package controller;

import connectionPool.ConnectionPool;
import dao.AuthDataDao;
import exception.ConnectionPoolException;
import exception.ExceptionLogger;
import exception.UsernameException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Egor on 05.11.2016.
 */
public class RegistrationController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_registration";

    public RegistrationController() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Set<String> keys = new HashSet<>(Arrays.asList("firstName", "lastName", "username", "password", "checkPassword"));
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
            if (!isPasswordEqual(request, response, messages, data)) {
                return;
            }
            insertUserToDb(request, response, messages, data);
            request.getRequestDispatcher("/signIn").forward(request, response);
            return;
        }
        request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        response.sendRedirect("/register");
    }

    private boolean isPasswordEqual(HttpServletRequest request, HttpServletResponse response,
                                           Map<String, String> messages, Map<String, String> data) throws ServletException, IOException {
        if (!data.get("password").equals(data.get("checkPassword"))) {
            messages.put("password", "Passwords do not match");
            response.sendRedirect("/register");
            return false;
        }
        return true;
    }

    private void insertUserToDb(HttpServletRequest request, HttpServletResponse response,
                                Map<String, String> messages, Map<String, String> data) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            AuthDataDao authDataDao = new AuthDataDao(connection);
            authDataDao.register(data.get("username"), data.get("password"), data.get("firstName"), data.get("lastName"));
        } catch (SQLException | ConnectionPoolException e) {
            ExceptionLogger.connectionException("INSERT USER TO DB", e);
            response.sendRedirect("/register");
        } catch (UsernameException e) {
            messages.put("username", "Username already exists");
            request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
            response.sendRedirect("/register");
        }
    }
}