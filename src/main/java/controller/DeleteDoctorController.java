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
    private static final String SUCCESS_MESSAGE_ATTRIBUTE = "success_message_doctors_table";

    public DeleteDoctorController() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        int id = Integer.parseInt(request.getParameter("doctorId"));
        boolean result = deleteDoctor(messages, id);
        if (result) {
            request.setAttribute(SUCCESS_MESSAGE_ATTRIBUTE, "doctorsTable.message.success.delete.doctor");
        }
        request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        //TODO: CONTROLLER
        request.getRequestDispatcher("./doctorsTableController").forward(request, response);
    }

    /**
     * Delete Doctor-class entity from DB using Dao
     * @param messages - map of messages for output
     * @param id - id of element to delete
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private boolean deleteDoctor(Map<String, String> messages, int id) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            DoctorDao doctorDao = new DoctorDao(connection);
            Doctor doctor = doctorDao.getById(id);
            doctorDao.delete(doctor);
            return true;
        } catch (SomeException | ConnectionPoolException | SQLException e) {
            messages.put("doctorsTable", "doctorsTable.message.fail.delete.doctor");
            ExceptionLogger.connectionException("DeleteDoctor - connection problem", e);
            return false;
        }
    }
}
