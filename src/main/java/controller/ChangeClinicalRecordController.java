package controller;

import connectionPool.ConnectionPool;
import dao.ClinicalRecordDao;
import exception.ConnectionPoolException;
import exception.ExceptionLogger;
import exception.SomeException;
import model.ClinicalRecord;

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
 * Created by Egor on 20.11.2016.
 */

@WebServlet("/changeClinicalRecordController")
public class ChangeClinicalRecordController  extends HttpServlet {

    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_clinical_records_table";
    private static final String SUCCESS_MESSAGE_ATTRIBUTE = "success_message_clinical_records_table";

    public ChangeClinicalRecordController() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        data.put("prescription", request.getParameter("prescription"));
        data.put("id", request.getParameter("clinicalRecordIdChange"));
        int updatedElementIndex = changeClinicalRecord(request, messages, data);
        if (updatedElementIndex > 0) {
            request.setAttribute(SUCCESS_MESSAGE_ATTRIBUTE, "clinicalRecordsTable.message.success.change.clinicalRecord");
        }
        request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        //TODO: CONTROLLER
        request.getRequestDispatcher("./clinicalRecordsTableController").forward(request, response);
    }

    /**
     * Change ClinicalRecord-class entity in DB using Dao
     * @param request - HttpServletRequest
     * @param messages - map of messages for output
     * @param data - map of parameters for method
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private int changeClinicalRecord(HttpServletRequest request, Map<String, String> messages,
                                     Map<String, String> data) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            ClinicalRecordDao clinicalRecordDao = new ClinicalRecordDao(connection);
            ClinicalRecord clinicalRecord = clinicalRecordDao.getById(Integer.parseInt(data.get("id")));
            String editedPrescription = String.format("%s; %s (выписал(а): %s)", clinicalRecord.getPrescription(),
                    data.get("prescription"), request.getSession().getAttribute("fullName"));
            clinicalRecord.setPrescription(editedPrescription);
            return clinicalRecordDao.update(clinicalRecord);
        } catch (SomeException | ConnectionPoolException | SQLException e) {
            messages.put("clinicalRecordsTable", "clinicalRecordsTable.message.fail.change.clinicalRecord");
            ExceptionLogger.connectionException("ChangeClinicalRecord - connection problem", e);
            return -1;
        }
    }
}