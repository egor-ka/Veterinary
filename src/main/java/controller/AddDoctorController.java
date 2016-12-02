package controller;

import connectionPool.ConnectionPool;
import dao.DoctorDao;
import exception.ConnectionPoolException;
import exception.ExceptionLogger;
import exception.SomeException;
import model.Doctor;

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

@WebServlet("/addDoctorController")
public class AddDoctorController extends HttpServlet{

    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_doctors_table";
    private static final String SUCCESS_MESSAGE_ATTRIBUTE = "success_message_doctors_table";

    public AddDoctorController() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> messages = new HashMap<>();
        Map<String, String> data = new HashMap<>();

        data.put("firstName", request.getParameter("firstName"));
        data.put("lastName", request.getParameter("lastName"));
        data.put("specialization", request.getParameter("specialization"));

        int inputElementIndex = inputDoctor(messages, data);

        if (inputElementIndex > 0) {
            request.setAttribute(SUCCESS_MESSAGE_ATTRIBUTE, "doctorsTable.message.success.add.doctor");
        }
        request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        //TODO: CONTROLLER
        request.getRequestDispatcher("./doctorsTableController").forward(request, response);
    }

    /**
     * Input Doctor-class entity into DB using Dao
     * @param messages - map of messages for output
     * @param data - map of parameters for method
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private int inputDoctor(Map<String, String> messages, Map<String, String> data)  throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            DoctorDao doctorDao = new DoctorDao(connection);
            Doctor doctor = new Doctor(data.get("firstName"), data.get("lastName"), data.get("specialization"));
            return doctorDao.insert(doctor);
        } catch (SomeException | ConnectionPoolException | SQLException e) {
            messages.put("doctorsTable", "doctorsTable.message.fail.add.doctor");
            ExceptionLogger.connectionException("InputDoctor - connection problem", e);
            return -1;
        }
    }
}
