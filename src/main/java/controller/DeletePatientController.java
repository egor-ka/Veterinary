package controller;

import connectionPool.ConnectionPool;
import dao.PatientDao;
import exception.ConnectionPoolException;
import exception.ExceptionLogger;
import exception.SomeException;
import model.Patient;

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
 * Created by Egor on 27.11.2016.
 */
@WebServlet("/deletePatientController")
public class DeletePatientController extends HttpServlet {

    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_patients_table";

    public DeletePatientController() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        response.setContentType("text/html");
        int id = Integer.parseInt(request.getParameter("patientsId"));
        boolean result = deletePatient(messages, id);
        if (result == true) {
            messages.put("patientsTable", "patientsTable.message.success.delete.patient");
        }
        request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        response.sendRedirect("./patientsTableController");
    }

    private boolean deletePatient(Map<String, String> messages, int id) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            PatientDao patientDao = new PatientDao(connection);
            Patient patient = patientDao.getById(id);
            patientDao.delete(patient);
            return true;
        } catch (SomeException | ConnectionPoolException | SQLException e) {
            messages.put("patientsTable", "patientsTable.message.fail.delete.patient");
            ExceptionLogger.connectionException("DeletePatient - connection problem", e);
            return false;
        }
    }
}