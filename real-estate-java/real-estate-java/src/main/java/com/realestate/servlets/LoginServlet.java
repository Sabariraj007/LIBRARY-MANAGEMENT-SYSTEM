package com.realestate.servlets;

import com.realestate.utils.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		int userId = DatabaseUtil.validateUser(email, password);

		if (userId > 0) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);
			response.sendRedirect("properties.html");
		} else {
			response.sendRedirect("login.html?error=Invalid credentials, try again.");
		}
	}
}
