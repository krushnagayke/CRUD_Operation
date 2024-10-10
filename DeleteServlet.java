package in.pwskills.nitin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String query = "delete from userr where id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");
        // Link the Bootstrap CSS
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        // Get the values
        int id = Integer.parseInt(req.getParameter("id"));

        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            pw.println("<h2 class='bg-danger text-light text-center'>MySQL Driver not found.</h2>");
            return;
        }

        // Generate the connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pwskills_nitinsir", "root", "itfirst@2112");
             PreparedStatement ps = con.prepareStatement(query)) {

            // Set the values
           ps.setInt(1, id);

            // Execute the query
            int count = ps.executeUpdate();
            pw.println("<div class='card' style='margin:auto;width:300px;margin-top:90px'>");
            if (count == 1) {
                pw.println("<h2 class='bg-success text-light text-center mb-0 border border-dark p-3'>Record Deleted Successfully</h2>");
            } else {
                pw.println("<h2 class='bg-danger text-light text-center mb-0'>Record Not Deleted</h2>");
            }
        } catch (SQLException se) {
            pw.println("<h2 class='bg-danger text-light text-center'>" + se.getMessage() + "</h2>");
            se.printStackTrace();
        }

        pw.println("</div>");
        pw.println("<div class='d-flex justify-content-center mt-4'>");
        pw.println("<a href='index.html'><button class='btn btn-outline-secondary'>Home</button></a>");
        pw.println("&nbsp; &nbsp;");
        pw.println("<a href='showdata'><button class='btn btn-outline-secondary'>Show User</button></a>");
        pw.println("</div>");

        // Close the stream
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }

}
