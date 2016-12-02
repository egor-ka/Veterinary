package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Egor on 06.11.2016.
 */

@WebServlet("/logOutController")
public class LogOutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LogOutController() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("username");
        request.getSession().removeAttribute("fullName");

        response.sendRedirect("./signIn");
    }
}
