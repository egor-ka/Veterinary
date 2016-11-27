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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        response.setContentType("text/html");

        if (request.getParameter("buttonExtraFeatures") != null) {
            Object attribute = request.getSession().getAttribute("extraFeaturesClinicalRecords");
            if (attribute != null) {
                if (attribute.toString() == "true") {
                    request.getSession().setAttribute("extraFeaturesClinicalRecords", false);
                } else {
                    request.getSession().setAttribute("extraFeaturesClinicalRecords", true);
                }
            } else {
                request.getSession().setAttribute("extraFeaturesClinicalRecords", true);
            }
        }

        List<List<String>> fullClinicalRecords = getAllClinicalRecords(request, response, messages);
        if (fullClinicalRecords == null) {
            request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
            response.sendRedirect("./clinicalRecordsTable");
            return;
        }
        if (fullClinicalRecords.size() == 0) {
            messages.put("clinicalRecordsTable", "There are no current clinicalRecords");
        }
        request.getSession().setAttribute(CLINICAL_RECORDS_TABLE_ATTRIBUTE, fullClinicalRecords);
        request.getSession().setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        response.sendRedirect("./clinicalRecordsTable");
    }

    private List<List<String>> getAllClinicalRecords(HttpServletRequest request, HttpServletResponse response,
                                                       Map<String, String> messages) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            ClinicalRecordDao clinicalRecordDao = new ClinicalRecordDao(connection);
            return clinicalRecordDao.getAllFullRecords();
        } catch (SQLException | ConnectionPoolException | SomeException e) {
            messages.put("clinicalRecordsTable", "Could not load clinical records, please try again later");
            ExceptionLogger.connectionException("GetAllClinicalRecords - connection problem", e);
            response.sendRedirect("./clinicalRecordsTable");
            return null;
        }
    }
}
