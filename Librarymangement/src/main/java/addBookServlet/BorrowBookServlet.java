package addBookServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BorrowBookServlet")
public class BorrowBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        PrintWriter out = response.getWriter();
        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/db";
        String user = "root";
        String password = "root";
        
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
        	String updateQuantityQuery = "UPDATE Books SET quantity = quantity - 1 WHERE id = ? AND quantity > 0";            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuantityQuery)) {
                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();
            }
            
            String selectSql = "SELECT * FROM Books";
            try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
                ResultSet rs = selectPstmt.executeQuery();

                // Output table rows dynamically
                out.println("<html><head><link rel=\"stylesheet\" href=\"ViewLibrary.css\"></head><body>");
                out.println("<table class='table table-dark my-3'>");
                out.println("<tbody id='table-body'>");
                int slNo = 1;
                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + slNo++ + "</td>");
                    out.println("<td>" + rs.getDate("Submission_Date") + "</td>");
                    out.println("<td>" + rs.getString("Auther_Name") + "</td>");
                    out.println("<td>" + rs.getString("Book_Name") + "</td>");
                    out.println("<td>" + rs.getString("Book_Type") + "</td>");
                    if (rs.getInt("quantity") > 0) {
                        out.println("<td>Available</td>");
                    } else {
                        out.println("<td>Not Available</td>");
                    }
                    out.println("<td>"+rs.getInt("quantity")+"</td>");
                    out.println("<td><button onclick=\"borrowBook(" + rs.getInt("id") + ")\">Borrow</button><br>");
                    out.println("<button onclick=\"returnBook(" + rs.getInt("id") + ")\">Return</button><br>");
                    out.println("<button onclick=\"deleteBook(" + rs.getInt("id") + ")\">Delete</button></td>");
                    out.println("</tr>");
                
                out.println("<script>");
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
        } catch (SQLException e) {
            out.println("<script>alert('An error occurred. Please try again later.');</script>");
            e.printStackTrace();
        }
        
    } catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}
}
