package controller;

import connectionPool.ConnectionPool;
import dao.AuthDataDao;
import exception.ConnectionPoolException;
import exception.ExceptionLogger;
import exception.UsernameException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Egor on 05.11.2016.
 */

@WebServlet("/registrationController")
public class RegistrationController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_registration";
    private static final String SUCCESS_MESSAGE_ATTRIBUTE = "success_message_sign_in";

    public RegistrationController() {
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
                messages.put(key, "registration.message.empty." + key);
            }
        }
        if (messages.size() == 0) {
            if (!isPasswordEqual(messages, data)) {
                request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
                request.getRequestDispatcher("./registration").forward(request, response);
            } else {
                if (insertUserToDb(response, messages, data)) {
                    request.setAttribute(SUCCESS_MESSAGE_ATTRIBUTE, "registration.message.success.registration");
                    request.getRequestDispatcher("./signIn").forward(request, response);
                }
            }
            request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
            return;
        }
        request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        request.getRequestDispatcher("./registration").forward(request, response);
    }

    /**
     * Checks two string fields for match
     * @param messages - map of messages for output
     * @param data - map of parameters for method
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private boolean isPasswordEqual(Map<String, String> messages, Map<String, String> data)
            throws ServletException, IOException {
        if (!data.get("password").equals(data.get("checkPassword"))) {
            messages.put("password", "registration.message.wrong.checkPassword");
            return false;
        }
        return true;
    }

    /**
     * Input ClinicalRecord-class entity into DB using Dao
     * @param response - {@link HttpServletResponse}
     * @param messages - map of messages for output
     * @param data - map of parameters for method
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private boolean insertUserToDb(HttpServletResponse response, Map<String, String> messages, Map<String, String> data)
            throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            AuthDataDao authDataDao = new AuthDataDao(connection);
            authDataDao.register(data.get("username"), data.get("password"), data.get("firstName"), data.get("lastName"));
            return true;
        } catch (SQLException | ConnectionPoolException e) {
            ExceptionLogger.connectionException("InsertUserToDb", e);
            messages.put("registration", "registration.message.fail.register");
            response.sendRedirect("./registration");
            return false;
        } catch (UsernameException e) {
            messages.put("username", "registration.message.fail.existing.username");
            response.sendRedirect("./registration");
            return false;
        }
    }
}