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
import java.util.List;
import java.util.Map;

/**
 * Created by Egor on 11.11.2016.
 */

@WebServlet("/patientsTableController")
public class PatientsTableController extends HttpServlet {

    private static final String PATIENTS_TABLE_ATTRIBUTE = "patients_table";
    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_patients_table";

    public PatientsTableController() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        if (request.getParameter("extraFeaturesName") != null) {
            String[] result = request.getParameterValues("extraFeaturesName");
            String attribute = result[0];
            ExceptionLogger.log(attribute);
            if (attribute != null) {
                if (attribute.equals("true")) {
                    request.setAttribute("extraFeaturesClinicalRecords", false);
                } else {
                    request.setAttribute("extraFeaturesClinicalRecords", true);
                }
            } else {
                request.setAttribute("extraFeaturesClinicalRecords", true);
            }
        }
        List<Patient> patients = getAllPatients(messages);
        if (patients == null) {
            request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
            request.getRequestDispatcher("./patientsTable").forward(request, response);
            return;
        }
        if (patients.size() == 0) {
            messages.put("patientsTable", "patientsTable.message.empty.patients");
        }
        request.setAttribute(PATIENTS_TABLE_ATTRIBUTE, patients);
        request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        request.getRequestDispatcher("./patientsTable").forward(request, response);
    }

    /**
     * Get all Patient-class entities from DB using Dao
     * @param messages - map of messages for output
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private List<Patient> getAllPatients(Map<String, String> messages) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            PatientDao patientDao = new PatientDao(connection);
            return patientDao.getAll();
        } catch (SQLException | ConnectionPoolException | SomeException e) {
            messages.put("patientsTable", "patientsTable.message.fail.load.patients");
            ExceptionLogger.connectionException("GetAllPatients - connection problem", e);
            return null;
        }
    }
}
