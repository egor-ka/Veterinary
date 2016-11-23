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
 * Created by Egor on 20.11.2016.
 */
public class DeleteClinicalRecordController extends HttpServlet {

    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_clinical_records_table";

    public DeleteClinicalRecordController() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        int id;// = Integer.parseInt();
        response.setContentType("text/html");

        data.put("text", request.getParameter("appendPrescription"));
        data.put("id", request.getParameter("clinicalRecordId"));
        int inputElementIndex = changeClinicalRecord(request, response, messages, data);

        if (inputElementIndex > 0) {
            messages.put("clinicalRecordsTable", "Clinical record successfully changed");
        }
        request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        response.sendRedirect("/clinicalRecordsTableController");
    }

    private int changeClinicalRecord(HttpServletRequest request, HttpServletResponse response,
                                     Map<String, String> messages, Map<String, String> data) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            ClinicalRecordDao clinicalRecordDao = new ClinicalRecordDao(connection);
            ClinicalRecord clinicalRecord = clinicalRecordDao.getById(1);
            String editedPrescription = clinicalRecord.getPrescription();

            return clinicalRecordDao.insert(clinicalRecord);
        } catch (SomeException | ConnectionPoolException | SQLException e) {
            messages.put("clinicalRecordsTable", "Could not change clinical record, due to connection problems, try again later");
            request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
            ExceptionLogger.connectionException("ChangeClinicalRecord - connection problem", e);
            return -1;
        }
    }
}