package com.realestate.servlets;

import com.realestate.utils.DatabaseUtil;
import com.realestate.utils.EmailUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/interest")
public class InterestServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");

		if (userId == null) {
			response.sendRedirect("login.html");
			return;
		}

		int propertyId = Integer.parseInt(request.getParameter("propertyId"));
		DatabaseUtil.registerInterest(userId, propertyId);

		String sellerEmail = DatabaseUtil.getSellerEmail(propertyId);
		String buyerEmail = DatabaseUtil.getUserEmail(userId);

		EmailUtil.sendInterestEmail(sellerEmail, buyerEmail);
		response.sendRedirect("properties.html");
	}
}
