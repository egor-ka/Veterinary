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
    private static final String SUCCESS_MESSAGE_ATTRIBUTE = "success_message_patients_table";

    public DeletePatientController() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        int id = Integer.parseInt(request.getParameter("patientsId"));
        boolean result = deletePatient(messages, id);
        if (result) {
            request.setAttribute(SUCCESS_MESSAGE_ATTRIBUTE, "patientsTable.message.success.delete.patient");
        }
        request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        //TODO: CONTROLLER
        request.getRequestDispatcher("./patientsTableController").forward(request, response);
    }

    /**
     * Delete Patient-class entity from DB using Dao
     * @param messages - map of messages for output
     * @param id - id of element to delete
     * @return
     * @throws ServletException
     * @throws IOException
     */
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