package controller;

import connectionPool.ConnectionPool;
import dao.ClinicalRecordDao;
import exception.ConnectionPoolException;
import exception.ExceptionLogger;
import exception.SomeException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Egor on 12.11.2016.
 */

@WebServlet("/clinicalRecordsTableController")
public class ClinicalRecordsTableController extends HttpServlet {

    private static final String CLINICAL_RECORDS_TABLE_ATTRIBUTE = "clinical_records_table";
    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_clinical_records_table";

    public ClinicalRecordsTableController() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        if (request.getParameter("extraFeaturesName") != null) {
            String[] result = request.getParameterValues("extraFeaturesName");
            String attribute = result[0];
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
        List<List<String>> fullClinicalRecords = getAllClinicalRecords(response, messages);
        if (fullClinicalRecords == null) {
            request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
            request.getRequestDispatcher("./clinicalRecordsTable").forward(request, response);
            return;
        }
        if (fullClinicalRecords.size() == 0) {
            messages.put("clinicalRecordsTable", "clinicalRecordsTable.message.empty.clinicalRecords");
        }
        request.setAttribute(CLINICAL_RECORDS_TABLE_ATTRIBUTE, fullClinicalRecords);
        request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        request.getRequestDispatcher("./clinicalRecordsTable").forward(request, response);
    }

    /**
     * Get all ClinicalRecord-class entities from DB using Dao
     * @param response - HttpServletResponse
     * @param messages - map of messages for output
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private List<List<String>> getAllClinicalRecords(HttpServletResponse response, Map<String, String> messages)
            throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            ClinicalRecordDao clinicalRecordDao = new ClinicalRecordDao(connection);
            return clinicalRecordDao.getAllFullRecords();
        } catch (SQLException | ConnectionPoolException | SomeException e) {
            messages.put("clinicalRecordsTable", "clinicalRecordsTable.message.fail.load.clinicalRecords");
            ExceptionLogger.connectionException("GetAllClinicalRecords - connection problem", e);
            return null;
        }
    }
}
