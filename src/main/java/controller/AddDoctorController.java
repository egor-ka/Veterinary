package controller;

import connectionPool.ConnectionPool;
import dao.DoctorDao;
import exception.ConnectionPoolException;
import exception.ExceptionLogger;
import exception.SomeException;
import model.Doctor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Egor on 13.11.2016.
 */
public class AddDoctorController extends HttpServlet{

    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_doctors_table";

    public AddDoctorController() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        response.setContentType("text/html");

        data.put("firstName", request.getParameter("firstName"));
        data.put("lastName", request.getParameter("lastName"));
        data.put("specialization", request.getParameter("specialization"));

        int inputElementIndex = inputDoctor(request, response, messages, data);

        if (inputElementIndex > 0) {
            messages.put("doctorsTable", "Doctor successfully added");
        }
        request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        response.sendRedirect("/doctorsTableController");
    }

    private int inputDoctor(HttpServletRequest request, HttpServletResponse response,
                                    Map<String, String> messages, Map<String, String> data)  throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            DoctorDao doctorDao = new DoctorDao(connection);
            Doctor doctor = new Doctor(data.get("firstName"), data.get("lastName"), data.get("specialization"));
            return doctorDao.insert(doctor);
        } catch (SomeException | ConnectionPoolException | SQLException e) {
            messages.put("doctorsTable", "Could not add doctor, due to connection problems, try again later");
            request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
            ExceptionLogger.connectionException("InputDoctor - connection problem", e);
            return -1;
        }
    }
}
