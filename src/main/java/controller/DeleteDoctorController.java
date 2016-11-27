package controller;

import connectionPool.ConnectionPool;
import dao.DoctorDao;
import exception.ConnectionPoolException;
import exception.ExceptionLogger;
import exception.SomeException;
import model.Doctor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Egor on 20.11.2016.
 */

@WebServlet("/deleteDoctorController")
public class DeleteDoctorController extends HttpServlet {

    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_doctors_table";

    public DeleteDoctorController() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        response.setContentType("text/html");

        int id = Integer.parseInt(request.getParameter("doctorId"));
        boolean result = deleteDoctor(messages, id);

        if (result == true) {
            messages.put("doctorsTable", "Doctor successfully deleted");
        }
        request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        response.sendRedirect("./doctorsTableController");
    }

    private boolean deleteDoctor(Map<String, String> messages, int id) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            DoctorDao doctorDao = new DoctorDao(connection);
            Doctor doctor = doctorDao.getById(id);
            doctorDao.delete(doctor);
            return true;
        } catch (SomeException | ConnectionPoolException | SQLException e) {
            messages.put("doctorsTable", "Could not delete doctor, due to connection problems, try again later");
            ExceptionLogger.connectionException("DeleteDoctor - connection problem", e);
            return false;
        }
    }
}
