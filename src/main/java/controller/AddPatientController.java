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
 * Created by Egor on 13.11.2016.
 */

@WebServlet("/addPatientController")
public class AddPatientController extends HttpServlet {

    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_patients_table";
    private static final String SUCCESS_MESSAGE_ATTRIBUTE = "success_message_patients_table";

    public AddPatientController() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        Map<String, String> data = new HashMap<>();

        data.put("ownerName", request.getParameter("ownerName"));
        data.put("petName", request.getParameter("petName"));
        data.put("petSpecies", request.getParameter("petSpecies"));

        int inputElementIndex = inputPatient(messages, data);

        if (inputElementIndex > 0) {
            request.setAttribute(SUCCESS_MESSAGE_ATTRIBUTE, "patientsTable.message.success.add.patient");
        }
        request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        //TODO: CONTROLLER
        request.getRequestDispatcher("./patientsTableController").forward(request, response);
    }

    /**
     * Input Patient-class entity into DB using Dao
     * @param messages - map of messages for output
     * @param data - map of parameters for method
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private int inputPatient(Map<String, String> messages, Map<String, String> data) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            PatientDao patientDao = new PatientDao(connection);
            Patient patient = new Patient(data.get("ownerName"), data.get("petName"), data.get("petSpecies"));
            return patientDao.insert(patient);
        } catch (SomeException | ConnectionPoolException | SQLException e) {
            messages.put("patientsTable", "patientsTable.message.fail.add.patient");
            ExceptionLogger.connectionException("InputPatient - connection problem", e);
            return -1;
        }
    }
}