package com.realestate.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.authenticator.jaspic.PersistentProviderRegistrations.Property;

public class DatabaseUtil {
	private static final String DB_URL = "jdbc:h2:~/realestate";
	private static final String USER = "sa";
	private static final String PASS = "";

	static {
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement()) {
			stmt.execute("CREATE TABLE IF NOT EXISTS users (...);");
			stmt.execute("CREATE TABLE IF NOT EXISTS properties (...);");
			stmt.execute("CREATE TABLE IF NOT EXISTS likes (...);");
			stmt.execute("CREATE TABLE IF NOT EXISTS interests (...);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean registerUser(String firstName, String lastName, String email, String phoneNumber,
			String password, String userType) {
		// Implement user registration logic
		// Hash the password before storing
		return true;
	}

	public static int validateUser(String email, String password) {
		// Implement user validation logic
		return 0;
	}

	public static void postProperty(int userId, String title, String description, String place, double area,
			int bedrooms, int bathrooms, String nearbyHospitals, String nearbyColleges) {
		// Implement property posting logic
	}

	public static List<Property> getAllProperties() {
		// Implement logic to retrieve all properties
		return new ArrayList<>();
	}

	public static String getSellerEmail(int propertyId) {
		// Implement logic to get the seller's email by propertyId
		return "";
	}

	public static String getUserEmail(int userId) {
		// Implement logic to get the user's email by userId
		return "";
	}

	public static void registerInterest(int userId, int propertyId) {
		// Implement logic to register interest
	}

	public static void likeProperty(int userId, int propertyId) {
		// Implement logic to like a property
	}
}
