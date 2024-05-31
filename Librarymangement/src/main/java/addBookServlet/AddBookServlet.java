package addBookServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve form data
        String authorName = request.getParameter("Auther_Name");
        String bookName = request.getParameter("Book-Name");
        String bookType = request.getParameter("check-box");
        if(authorName!=null && bookName!=null && bookType !=null ) {
        	// Get current date
            LocalDate currentDate = LocalDate.now();

            // Database connection parameters
            String url = "jdbc:mysql://localhost:3306/db";
            String user = "root";
            String password = "root";

            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                
                String checkIfExistsQuery = "SELECT id, quantity FROM Books WHERE Auther_Name = ? AND Book_Name = ? AND Book_Type = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(checkIfExistsQuery)) {
                    pstmt.setString(1, authorName);
                    pstmt.setString(2, bookName);
                    pstmt.setString(3, bookType);
                    ResultSet rs1 = pstmt.executeQuery();

                    if (rs1.next()) {
                        // Book already exists, update quantity
                        int bookId = rs1.getInt("id");
                        int currentQuantity = rs1.getInt("quantity");

                        String updateQuantityQuery = "UPDATE Books SET quantity = ? WHERE id = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuantityQuery)) {
                            updateStmt.setInt(1, currentQuantity + 1);
                            updateStmt.setInt(2, bookId);
                            updateStmt.executeUpdate();
                        }
                    } else {
                        
                        String insertQuery = "INSERT INTO Books (Auther_Name, Book_Name, Book_Type, Submission_Date, quantity) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                            insertStmt.setString(1, authorName);
                            insertStmt.setString(2, bookName);
                            insertStmt.setString(3, bookType);
                            insertStmt.setDate(4, Date.valueOf(currentDate));
                            insertStmt.setInt(5, 1);
                            insertStmt.executeUpdate();
                        }
                    }
                }

                // Prepare SQL statement to retrieve all books
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
            } catch (SQLException e) {
                out.println("<script>alert('An error occurred. Please try again later.');</script>");
                e.printStackTrace();
            }
        }else {
        	out.println("<script>alert('An error occured adding Book, Please Enter Values.');</script>");
        	response.sendRedirect("Library.html");
        }
        
    }
}
