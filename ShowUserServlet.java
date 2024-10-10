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

import com.mysql.cj.xdevapi.Table;

/**
 * Servlet implementation class ShowUserServlet
 */
@WebServlet("/showdata")
public class ShowUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String query = "select id,name,email,mobile,dob,city,gender from userr";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");
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
        	ResultSet rs = ps.executeQuery();
        	pw.println("<div style='margin:auto; width:800px; margin-top:80px;'>");
        	pw.println("<table class='table table-hover table-striped'>");
        	pw.println("<table class='table table-hover table-striped'>");
            pw.println("<tr>");
            pw.println("<th>ID</th>");
            pw.println("<th>Name</th>");
            pw.println("<th>Email</th>");
            pw.println("<th>Mobile No</th>");
            pw.println("<th>DOB</th>");
            pw.println("<th>City</th>");
            pw.println("<th>Gender</th>");
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");
            while(rs.next()) {
                pw.println("<tr>");
                pw.println("<td>"+rs.getInt(1)+"</td>");
                pw.println("<td>"+rs.getString(2)+"</td>");
                pw.println("<td>"+rs.getString(3)+"</td>");
                pw.println("<td>"+rs.getString(4)+"</td>");
                pw.println("<td>"+rs.getString(5)+"</td>");
                pw.println("<td>"+rs.getString(6)+"</td>");
                pw.println("<td>"+rs.getString(7)+"</td>");
                pw.println("<td><a href='editurl?id="+rs.getInt(1)+"'>Edit</a></td>");
                pw.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'>Delete</a></td>");
                pw.println("</tr>");
            }
            
            
        	pw.println("<table>");
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
