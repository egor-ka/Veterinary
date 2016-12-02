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
import java.util.List;
import java.util.Map;

/**
 * Created by Egor on 11.11.2016.
 */

@WebServlet("/doctorsTableController")
public class DoctorsTableController extends HttpServlet{
    
    private static final String DOCTORS_TABLE_ATTRIBUTE = "doctors_table";
    private static final String ERROR_MESSAGES_ATTRIBUTE = "error_messages_doctors_table";

    public DoctorsTableController() {
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
                    request.setAttribute("extraFeaturesDoctors", false);
                } else {
                    request.setAttribute("extraFeaturesDoctors", true);
                }
            } else {
                request.setAttribute("extraFeaturesDoctors", true);
            }
        }
        List<Doctor> doctors = getAllDoctors(messages);
        if (doctors == null) {
            request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
            request.getRequestDispatcher("./doctorsTable").forward(request, response);
            return;
        }
        if (doctors.size() == 0) {
            messages.put("doctorsTable", "doctorsTable.message.empty.doctors");
        }
        request.setAttribute(DOCTORS_TABLE_ATTRIBUTE, doctors);
        request.setAttribute(ERROR_MESSAGES_ATTRIBUTE, messages);
        request.getRequestDispatcher("./doctorsTable").forward(request, response);
    }

    /**
     * Get all Doctor-class entities from DB using Dao
     * @param messages - map of messages for output
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private List<Doctor> getAllDoctors(Map<String, String> messages) throws ServletException, IOException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (Connection connection = connectionPool.takeConnection()) {
            DoctorDao doctorDao = new DoctorDao(connection);
            return doctorDao.getAll();
        } catch (SQLException | ConnectionPoolException | SomeException e) {
            messages.put("doctorsTable", "doctorsTable.message.fail.load.doctors");
            ExceptionLogger.connectionException("GetAllDoctors - connection problem", e);
            return null;
        }
    }
}
