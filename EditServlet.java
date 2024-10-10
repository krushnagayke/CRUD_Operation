package in.pwskills.nitin.controller;

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

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/editurl")
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String query = "select name,email,mobile,dob,city,gender from userr where id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        
        // Set content type
        res.setContentType("text/html");
        
        // get id
        int id = Integer.parseInt(req.getParameter("id"));
        // Link the Bootstrap CSS
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        pw.println("<marquee><h2 class='text-danger'>User Data</h2></marquee>");
        

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
        	ps.setInt(1, id);
        	ResultSet rs = ps.executeQuery();
        	rs.next();
            pw.println("<div style='margin:auto;width:500px;margin-top:100px;'>");
            pw.println("<form action='edit?id="+id+"' method='post'>");
            pw.println("<table class='table table-hover table-striped'>");
            pw.println("<tr>");
            pw.println("<td>Name</td>");
            pw.println("<td><input type='text' name='name' value='"+rs.getString(1)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Email</td>");
            pw.println("<td><input type='email' name='email' value='"+rs.getString(2)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Mobile</td>");
            pw.println("<td><input type='text' name='mobile' value='"+rs.getString(3)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>DOB</td>");
            pw.println("<td><input type='date' name='dob' value='"+rs.getString(4)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>City</td>");
            pw.println("<td><input type='text' name='city' value='"+rs.getString(5)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td>Gender</td>");
            pw.println("<td><input type='text' name='gender' value='"+rs.getString(6)+"'></td>");
            pw.println("</tr>");
            pw.println("<tr>");
            pw.println("<td><button type='submit' class='btn btn-outline-success'>Edit</button></td>");
            pw.println("<td><button type='reset' class='btn btn-outline-danger'>Cancel</button></td>");
            pw.println("</tr>");
            pw.println("</table>");
            pw.println("</form>");
      
        } catch (SQLException se) {
            pw.println("<h2 class='bg-danger text-light text-center'>" + se.getMessage() + "</h2>");
            se.printStackTrace();
        }

        pw.println("</div>");
        pw.println("<div class='d-flex justify-content-center mt-4'>");
        pw.println("<a href='index.html'><button class='btn btn-outline-secondary'>Home</button></a>");
        pw.println("</div>");

        // Close the stream
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }

}
