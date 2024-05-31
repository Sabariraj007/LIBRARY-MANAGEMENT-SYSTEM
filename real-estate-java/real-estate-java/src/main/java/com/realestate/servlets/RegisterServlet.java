package com.realestate.servlets;

import com.realestate.utils.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		String password = request.getParameter("password");
		String userType = request.getParameter("userType");

		boolean isRegistered = DatabaseUtil.registerUser(firstName, lastName, email, phoneNumber, password, userType);

		if (isRegistered) {
			response.sendRedirect("login.html");
		} else {
			response.sendRedirect("register.html?error=Registration failed, try again.");
		}
	}
}
