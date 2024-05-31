package com.realestate.servlets;

import com.realestate.utils.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.authenticator.jaspic.PersistentProviderRegistrations.Property;

import java.io.IOException;
import java.util.List;

@WebServlet("/postProperty")
public class PropertyServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");

		if (userId == null) {
			response.sendRedirect("login.html");
			return;
		}

		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String place = request.getParameter("place");
		double area = Double.parseDouble(request.getParameter("area"));
		int bedrooms = Integer.parseInt(request.getParameter("bedrooms"));
		int bathrooms = Integer.parseInt(request.getParameter("bathrooms"));
		String nearbyHospitals = request.getParameter("nearbyHospitals");
		String nearbyColleges = request.getParameter("nearbyColleges");

		DatabaseUtil.postProperty(userId, title, description, place, area, bedrooms, bathrooms, nearbyHospitals,
				nearbyColleges);
		response.sendRedirect("properties.html");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Property> properties = DatabaseUtil.getAllProperties();
		request.setAttribute("properties", properties);
		request.getRequestDispatcher("views/properties.jsp").forward(request, response);
	}
}
