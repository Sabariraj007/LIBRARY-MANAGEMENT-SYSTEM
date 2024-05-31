package viewLirary;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class ViewLirary
 */
@WebServlet("/ViewLibrary")
public class ViewLibrary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ViewLibrary() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		 String url = "jdbc:mysql://localhost:3306/db";
	        String user = "root";
	        String password = "root";

	        try {
	            // Load MySQL JDBC Driver
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            
	            // Create Connection
	            Connection conn = DriverManager.getConnection(url, user, password);
	            String selectSql = "SELECT * FROM Books";
	            try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
	                ResultSet rs = selectPstmt.executeQuery();

	                out.println("<html><head><link rel=\"stylesheet\" href=\"ViewLibrary.css\"></head><body>");
	                out.println("<table class='table table-dark my-3'>");
	                out.println("<thead><tr><th scope='col'>Sl No.</th><th scope='col'>Date of issue</th><th scope='col'>Author Name</th><th scope='col'>Book Name</th><th scope='col'>Book Type</th><th scope='col'>Availability</th><th scope='col'>Quantity</th><th scope='col'>Actions</th></tr></thead>");
	                out.println("<tbody id='table-body'>");
	                int slNo = 1;
	                while (rs.next()) {
	                    out.println("<tr>");
	                    out.println("<td>" + slNo++ + "</td>");
	                    out.println("<td>" + rs.getDate("Submission_Date") + "</td>");
	                    out.println("<td>" + rs.getString("Auther_Name") + "</td>");
	                    out.println("<td>" + rs.getString("Book_Name") + "</td>");
	                    out.println("<td>" + rs.getString("Book_Type") + "</td>");
	                    out.println("<td>" + (rs.getBoolean("Availability") ? "Available" : "Not Available") + "</td>");
	                    out.println("<td>"+rs.getInt("quantity")+"</td>");
	                    out.println("<td><button onclick=\"borrowBook(" + rs.getInt("id") + ")\">Borrow</button><br>");
	                    out.println("<button onclick=\"returnBook(" + rs.getInt("id") + ")\">Return</button><br>");
	                    out.println("<button onclick=\"deleteBook(" + rs.getInt("id") + ")\">Delete</button></td>");
	                    out.println("</tr>");
	                }
	                out.println("</tbody></table><button id=\"redirectButton\">Return to Library</button>");
	                out.println("<script>"
	                		+ "        var button = document.getElementById(\"redirectButton\");\r\n"
	    	        		+ "        button.addEventListener(\"click\", function() {\r\n"
	    	        		+ "            window.location.href = \"Library.html\";\r\n"
	    	        		+ "        });\r\n");
	                out.println("function borrowBook(bookId) {");
	                out.println("    var xhr = new XMLHttpRequest();");
	                out.println("    xhr.open('POST', 'BorrowBookServlet?bookId=' + bookId, true);");
	                out.println("    xhr.send();");
	                out.println("    xhr.onload = function() {");
	                out.println("      if (xhr.status == 200) {");
	                out.println("        document.getElementById('table-body').innerHTML = xhr.responseText;");
	                out.println("      } else {");
	                out.println("        alert('An error occurred. Please try again later.');");
	                out.println("      }");
	                out.println("    };");
	                out.println("}");
	                out.println("function returnBook(bookId) {");
	                out.println("    var xhr = new XMLHttpRequest();");
	                out.println("    xhr.open('POST', 'ReturnBookServlet?bookId=' + bookId, true);");
	                out.println("    xhr.send();");
	                out.println("    xhr.onload = function() {");
	                out.println("      if (xhr.status == 200) {");
	                out.println("        document.getElementById('table-body').innerHTML = xhr.responseText;");
	                out.println("      } else {");
	                out.println("        alert('An error occurred. Please try again later.');");
	                out.println("      }");
	                out.println("    };");
	                out.println("}");
	                out.println("function deleteBook(bookId) {");
	                out.println("    var xhr = new XMLHttpRequest();");
	                out.println("    xhr.open('POST', 'DeleteBookServlet?bookId=' + bookId, true);");
	                out.println("    xhr.send();");
	                out.println("    xhr.onload = function() {");
	                out.println("      if (xhr.status == 200) {");
	                out.println("        document.getElementById('table-body').innerHTML = xhr.responseText;");
	                out.println("      } else {");
	                out.println("        alert('An error occurred. Please try again later.');");
	                out.println("      }");
	                out.println("    };");
	                out.println("}");
	                out.println("</script>");
	                out.println("</body></html>");
	            }

	            conn.close();
	        } catch (Exception e) {
	            out.println("<script>alert('An error occurred. Please try again later.');</script>");
	            e.printStackTrace();
	        }
	}

}
