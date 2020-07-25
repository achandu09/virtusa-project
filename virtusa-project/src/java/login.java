/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//import com.sun.jdi.connect.spi.Connection;
//import java.beans.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager; 
import java.sql.SQLException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author A Chandu
 */
public class login extends HttpServlet {
    protected static Connection initializeDatabase() 
        throws SQLException, ClassNotFoundException 
    { 
        // Initialize all the information regarding 
        // Database Connection 
        String dbDriver = "com.mysql.jdbc.Driver"; 
        String dbURL = "jdbc:mysql:// localhost:3306/"; 
        // Database name to access 
        String dbName = "epass"; 
        String dbUsername = "ac43"; 
        String dbPassword = "chandu456"; 
  
        Class.forName(dbDriver); 
        Connection con = (Connection) DriverManager.getConnection(dbURL + dbName, 
                                                     dbUsername,  
                                                     dbPassword); 
        return con; 
    } 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String usern=request.getParameter("uname");
        String userp=request.getParameter("upass");
        HttpSession ses=null;
        if(usern==null || userp == null ){
            response.sendRedirect("/virtusa-project");
        }
        else{
                  Connection conn = null;
		  String url = "jdbc:mysql://localhost:3306/epass";
		  String driver = "com.mysql.jdbc.Driver";
		  String userName = "ac43"; 
		  String password = "chandu456";
		  try {
		  Class.forName(driver);
		  conn = DriverManager.getConnection(url,userName,password);
		  //out.print("Connected to the database");
                  Statement st = conn.createStatement();
                  String q="select pass from details where username = "+"\""+usern+"\"";
                  ResultSet rs=st.executeQuery(q);
                  if(rs.next()){
                      if(userp.equals(rs.getString("pass"))){
                          ses=request.getSession();
                          ses.setAttribute("uname",usern);
                          response.sendRedirect("home");
                      }
                      else{
                          response.sendRedirect("login-failed");
                      }
                  }
                  else{
                      response.sendRedirect("login-failed");
                  }
		  conn.close();
		  //out.print("Disconnected from database");
		  } catch (Exception e) {
                      out.print(e);
		  }
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
