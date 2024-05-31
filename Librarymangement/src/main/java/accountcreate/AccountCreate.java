package accountcreate;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/AccountCreate")
public class AccountCreate extends HttpServlet {
	/**
	 * 
	 */private static final long serialVersionUID = 1L;
	
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    PrintWriter out = response.getWriter();
			String username = request.getParameter("Name");
		    String password = request.getParameter("password");
		    String confirmPassword = request.getParameter("confirm-password");
            boolean bool=password.equals(confirmPassword);
            System.out.println(bool);
		    
		    if (bool) {
		    	System.out.println("password checked");
		    	
		    	String url = "jdbc:mysql://localhost:3306/db";
		        String user = "root";
		        String dbPassword = "root";

		        try {
		        	 Class.forName("com.mysql.cj.jdbc.Driver");
		        	Connection conn = DriverManager.getConnection(url, user, dbPassword);
		            String insertSql = "INSERT INTO users (username, passwords) VALUES (?, ?)";
		            PreparedStatement pstmt = conn.prepareStatement(insertSql);
		                pstmt.setString(1, username);
		                pstmt.setString(2, password);
		                int rowsInserted = pstmt.executeUpdate();

		                if (rowsInserted > 0) {
		                	response.setContentType("text/html");
		                	out.println("<!DOCTYPE html>");
		        	        out.println("<html>");
		        	        out.println("<head>");
		        	        out.println("<title>Success Page</title><style>body {\r\n"
		        	        		+ "	\r\n"
		        	        		+ "\r\n"
		        	        		+ "	display: flex;\r\n"
		        	        		+ "	align-items: center;\r\n"
		        	        		+ "	justify-content: center;\r\n"
		        	        		+ "	font-family: sans-serif;\r\n"
		        	        		+ "	line-height: 1.5;\r\n"
		        	        		+ "	min-height: 100vh;\r\n"
		        	        		+ "	background: #f3f3f3;\r\n"
		        	        		+ "	flex-direction: column;\r\n"
		        	        		+ "	margin: 0;\r\n"
		        	        		+ "}button {\r\n"
		        	        		+ "	padding: 15px;\r\n"
		        	        		+ "	border-radius: 10px;\r\n"
		        	        		+ "	margin-top: 15px;\r\n"
		        	        		+ "	margin-bottom: 15px;\r\n"
		        	        		+ "	border: none;\r\n"
		        	        		+ "	color: white;\r\n"
		        	        		+ "	cursor: pointer;\r\n"
		        	        		+ "	background-color: #4CAF50;\r\n"
		        	        		+ "	width: 40%;\r\n"
		        	        		+ "	font-size: 16px;\r\n"
		        	        		+ "}h1 {\r\n"
		        	        		+ "	color: #4CAF50;\r\n"
		        	        		+ "}"
		        	        		+ "body {\r\n"
		        	        		+ "  background-image: url('https://www.innobytess.com/assets/images/background.png');\r\n"
		        	        		+ "}\r\n"
		        	        		+ "</style>");
		        	        out.println("</head>");
		        	        out.println("<body>");
		        	        out.println("<h1>Account Created Successfully!</h1><br>");
		        	        out.println("<button id=\"redirectButton\">login.</button>");
		        	        out.print("<script>\r\n"
		        	        		+ "        "
		        	        		+ "        var button = document.getElementById(\"redirectButton\");\r\n"
		        	        		+ "\r\n"
		        	        		+ "        "
		        	        		+ "        button.addEventListener(\"click\", function() {\r\n"
		        	        		+ "            // Redirect to the login page\r\n"
		        	        		+ "            window.location.href = \"index.jsp\";\r\n"
		        	        		+ "        });\r\n"
		        	        		+ "    </script>");
		        	        out.println("</body>");
		        	        out.println("</html>");
		                }else {
		                	response.sendRedirect("AccountCreate.html");
		                }
		            
		        } catch (SQLException e) {
					
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }else {
		    	System.out.println("password unchecked");

            	response.sendRedirect("AccountCreate.html");
            }
	 }
	        
	        

	    
	   
	   
}
