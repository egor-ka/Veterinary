package controller;

import connectionPool.ConnectionPool;
import dao.ClinicalRecordDao;
import exception.ConnectionPoolException;
import exception.ExceptionLogger;
import exception.SomeException;
import model.ClinicalRecord;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Egor on 12.11.2016.
 */
public class AddClinicalRecordController extends HttpServlet{

    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_clinical_records_table";

    public AddClinicalRecordController() {
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

        data.put("doctorId", request.getParameter("selectedDoctorId"));
        data.put("patientId", request.getParameter("selectedPatientId"));
        data.put("prescription", request.getParameter("prescription"));

        int inputElementIndex = inputClinicalRecord(request, response, messages, data);

        if (inputElementIndex > 0) {
            messages.put("clinicalRecordsTable", "Clinical record successfully added");
        }
        request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        response.sendRedirect("/clinicalRecordsTableController");
    }

    private int inputClinicalRecord(HttpServletRequest request, HttpServletResponse response,
                                     Map<String, String> messages, Map<String, String> data)  throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            ClinicalRecordDao clinicalRecordDao = new ClinicalRecordDao(connection);
            ClinicalRecord clinicalRecord = new ClinicalRecord(Integer.parseInt(data.get("doctorId")),
                    Integer.parseInt(data.get("patientId")),
                    data.get("prescription"));
            return clinicalRecordDao.insert(clinicalRecord);
        } catch (SomeException | ConnectionPoolException | SQLException e) {
            messages.put("clinicalRecordsTable", "Could not add clinical record, due to connection problems, try again later");
            request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
            ExceptionLogger.connectionException("InputClinicalRecord - connection problem", e);
            return -1;
        }
    }
}
