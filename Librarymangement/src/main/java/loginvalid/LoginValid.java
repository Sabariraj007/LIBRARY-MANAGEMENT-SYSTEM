package loginvalid;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class LoginValid
 */
@WebServlet("/LoginValid")
public class LoginValid extends HttpServlet {
	

	private static final long serialVersionUID = -2148945972546517189L;
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	 String Name=request.getParameter("Name");
	 String Pass=request.getParameter("password");
	 String url = "jdbc:mysql://localhost:3306/db";
     String user = "root";
     String dbPassword = "root";
     
     try  {
    	 Class.forName("com.mysql.cj.jdbc.Driver");
    	 Connection conn = DriverManager.getConnection(url, user, dbPassword);
         String sql = "SELECT * FROM users WHERE username = ? AND passwords = ?";
         PreparedStatement pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, Name);
             pstmt.setString(2, Pass);
             ResultSet rs = pstmt.executeQuery();

             if (rs.next()) {
	      			System.out.println("login success");
	      			response.sendRedirect("Library.html");
             } else{
	      			System.out.println("login failed");
	      			response.sendRedirect("index.jsp");
	      		}
         }
		      		
        
		catch (Exception e) {
	         
            e.printStackTrace();
        }
    }
}
	


