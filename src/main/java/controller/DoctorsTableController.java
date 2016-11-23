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
import java.util.List;
import java.util.Map;

/**
 * Created by Egor on 11.11.2016.
 */
public class DoctorsTableController extends HttpServlet{
    
    private static final String DOCTORS_TABLE_ATTRIBUTE = "doctors_table";
    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_doctors_table";

    public DoctorsTableController() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        response.setContentType("text/html");

        List<Doctor> doctors = getAllDoctors(request, response, messages);
        if (doctors == null) {
            return;
        }
        if (doctors.size() == 0) {
            messages.put("doctorsTable", "There are no current doctors");
        }
        request.getSession().setAttribute(DOCTORS_TABLE_ATTRIBUTE, doctors);

        request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        response.sendRedirect("/doctorsTable");
    }

    private List<Doctor> getAllDoctors(HttpServletRequest request, HttpServletResponse response,
                                         Map<String, String> messages) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            DoctorDao doctorDao = new DoctorDao(connection);
            return doctorDao.getAll();
        } catch (SQLException | ConnectionPoolException | SomeException e) {
            messages.put("doctorsTable", "Could not load doctors, please try again later");
            request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
            ExceptionLogger.connectionException("GetAllDoctors - connection problem", e);
            response.sendRedirect("/doctorsTable");
            return null;
        }
    }
}
