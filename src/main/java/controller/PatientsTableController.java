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
        response.setContentType("text/html");
        if (request.getParameter("buttonExtraFeatures") != null) {
            Object attribute = request.getSession().getAttribute("extraFeaturesPatients");
            if (attribute != null) {
                if (attribute.toString() == "true") {
                    request.getSession().setAttribute("extraFeaturesPatients", false);
                } else {
                    request.getSession().setAttribute("extraFeaturesPatients", true);
                }
            } else {
                request.getSession().setAttribute("extraFeaturesPatients", true);
            }
        }
        List<Patient> patients = getAllPatients(request, response, messages);
        if (patients == null) {
            request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
            response.sendRedirect("./patientsTable");
            return;
        }
        if (patients.size() == 0) {
            messages.put("patientsTable", "patientsTable.message.empty.patients");
        }
        request.getSession().setAttribute(PATIENTS_TABLE_ATTRIBUTE, patients);
        request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        response.sendRedirect("./patientsTable");
    }

    private List<Patient> getAllPatients(HttpServletRequest request, HttpServletResponse response,
                                         Map<String, String> messages) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            PatientDao patientDao = new PatientDao(connection);
            return patientDao.getAll();
        } catch (SQLException | ConnectionPoolException | SomeException e) {
            messages.put("patientsTable", "patientsTable.message.fail.load.patients");
            ExceptionLogger.connectionException("GetAllPatients - connection problem", e);
            response.sendRedirect("./patientsTable");
            return null;
        }
    }
}
