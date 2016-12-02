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

@WebServlet("/deleteClinicalRecordController")
public class DeleteClinicalRecordController extends HttpServlet {

    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_clinical_records_table";
    private static final String SUCCESS_MESSAGE_ATTRIBUTE = "success_message_clinical_records_table";

    public DeleteClinicalRecordController() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        int id = Integer.parseInt(request.getParameter("clinicalRecordIdDelete"));
        boolean result = deleteClinicalRecord(messages, id);
        if (result) {
            request.setAttribute(SUCCESS_MESSAGE_ATTRIBUTE, "clinicalRecordsTable.message.success.delete.clinicalRecord");
        }
        request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        //TODO: CONTROLLER
        request.getRequestDispatcher("./clinicalRecordsTableController").forward(request, response);
    }

    /**
     * Delete ClinicalRecord-class entity from DB using Dao
     * @param messages - map of messages for output
     * @param id - id of element to delete
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private boolean deleteClinicalRecord(Map<String, String> messages, int id) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            ClinicalRecordDao clinicalRecordDao = new ClinicalRecordDao(connection);
            ClinicalRecord clinicalRecord = clinicalRecordDao.getById(id);
            clinicalRecordDao.delete(clinicalRecord);
            return true;
        } catch (SomeException | ConnectionPoolException | SQLException e) {
            messages.put("clinicalRecordsTable", "clinicalRecordsTable.message.fail.delete.clinicalRecord");
            ExceptionLogger.connectionException("DeleteClinicalRecord - connection problem", e);
            return false;
        }
    }
}