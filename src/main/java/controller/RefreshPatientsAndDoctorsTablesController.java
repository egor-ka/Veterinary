package controller;

import connectionPool.ConnectionPool;
import dao.DoctorDao;
import dao.PatientDao;
import exception.ConnectionPoolException;
import exception.ExceptionLogger;
import exception.SomeException;
import model.Doctor;
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
 * Created by Egor on 13.11.2016.
 */

@WebServlet("/refreshPatientsAndDoctorsTablesController")
public class RefreshPatientsAndDoctorsTablesController extends HttpServlet{

    private static final String PATIENTS_TABLE_ATTRIBUTE = "patients_table";
    private static final String DOCTORS_TABLE_ATTRIBUTE = "doctors_table";
    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_clinical_records_table";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        response.setContentType("text/html");

        List<Doctor> doctors = getAllDoctors(request, response, messages);
        List<Patient> patients = getAllPatients(request, response, messages);

        if (patients == null || doctors == null) {
            request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
            response.sendRedirect("./clinicalRecordsTable");
            return;
        }
        if (doctors.size() == 0) {
            messages.put("doctorsTable", "clinicalRecordsTable.message.empty.doctors");
        }
        if (patients.size() == 0) {
            messages.put("patientsTable", "clinicalRecordsTable.message.empty.patients");
        }
        request.getSession().setAttribute(DOCTORS_TABLE_ATTRIBUTE, doctors);
        request.getSession().setAttribute(PATIENTS_TABLE_ATTRIBUTE, patients);
        request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        response.sendRedirect("./addClinicalRecord");
    }

    private List<Doctor> getAllDoctors(HttpServletRequest request, HttpServletResponse response,
                                       Map<String, String> messages) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            DoctorDao doctorDao = new DoctorDao(connection);
            return doctorDao.getAll();
        } catch (SQLException | ConnectionPoolException | SomeException e) {
            messages.put("doctorsTable", "clinicalRecordsTable.message.fail.load.doctors");
            ExceptionLogger.connectionException("GetAllDoctors - connection problem", e);
            return null;
        }
    }

    private List<Patient> getAllPatients(HttpServletRequest request, HttpServletResponse response,
                                         Map<String, String> messages) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            PatientDao patientDao = new PatientDao(connection);
            return patientDao.getAll();
        } catch (SQLException | ConnectionPoolException | SomeException e) {
            messages.put("patientsTable", "clinicalRecordsTable.message.fail.load.patients");
            ExceptionLogger.connectionException("GetAllPatients - connection problem", e);
            return null;
        }
    }
}
