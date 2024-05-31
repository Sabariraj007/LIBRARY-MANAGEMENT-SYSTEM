package com.realestate.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtil {
	public static void sendInterestEmail(String sellerEmail, String buyerEmail) {
		try {
			SimpleEmail email = new SimpleEmail();
			email.setHostName("smtp.example.com");
			email.setSmtpPort(587);
			email.setAuthentication("your-email@example.com", "your-email-password");
			email.setSSLOnConnect(true);
			email.setFrom("no-reply@realestate.com");
			email.setSubject("Property Interest");
			email.setMsg("A buyer has shown interest in your property. Buyer Email: " + buyerEmail);
			email.addTo(sellerEmail);
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
