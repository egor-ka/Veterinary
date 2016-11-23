package controller;

import connectionPool.ConnectionPool;
import dao.PatientDao;
import exception.ConnectionPoolException;
import exception.ExceptionLogger;
import exception.SomeException;
import model.Patient;

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
 * Created by Egor on 13.11.2016.
 */
public class AddPatientController extends HttpServlet {

    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_patients_table";

    public AddPatientController() {
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

        data.put("ownerName", request.getParameter("ownerName"));
        data.put("petName", request.getParameter("petName"));
        data.put("petSpecies", request.getParameter("petSpecies"));

        int inputElementIndex = inputPatient(request, response, messages, data);

        if (inputElementIndex > 0) {
            messages.put("patientsTable", "Patient successfully added");
        }
        request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        response.sendRedirect("/patientsTableController");
    }

    private int inputPatient(HttpServletRequest request, HttpServletResponse response,
                            Map<String, String> messages, Map<String, String> data) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            PatientDao patientDao = new PatientDao(connection);
            Patient patient = new Patient(data.get("ownerName"), data.get("petName"), data.get("petSpecies"));
            return patientDao.insert(patient);
        } catch (SomeException | ConnectionPoolException | SQLException e) {
            messages.put("patientsTable", "Could not add patient, due to connection problems, try again later");
            request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
            ExceptionLogger.connectionException("InputPatient - connection problem", e);
            return -1;
        }
    }
}