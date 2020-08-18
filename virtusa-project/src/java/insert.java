/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author A Chandu
 */
@MultipartConfig(maxFileSize = 169999999)
public class insert extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession ses = request.getSession();
        PrintWriter out = response.getWriter();
        if(request.getParameter("uname")==null){
            response.sendRedirect("/virtusa-project/api/register");
        }
        else{
            String fname1=request.getParameter("fname");
            String lname1=request.getParameter("lname");
            String email1=request.getParameter("email");
            String phone1=String.valueOf(request.getParameter("phone"));
            String user1=request.getParameter("uname");
            String pass1=request.getParameter("upass");
            String uidai1=String.valueOf(request.getParameter("uidai"));
            Part file=request.getPart("document");
            InputStream is=null;
            if(file==null){
                out.print("Invalid File Type");
            }
            else{
                Connection conn = null;
		  String url = "jdbc:mysql://localhost:3306/epass";
		  String driver = "com.mysql.jdbc.Driver";
		  String userName = "ac43"; 
		  String password = "chandu456";
                  is=file.getInputStream();
                  try {
		  Class.forName(driver);
		  conn = java.sql.DriverManager.getConnection(url,userName,password);
		  //out.print("Connected to the database");
                  PreparedStatement ps = conn.prepareStatement("insert into details values(?,?,?,?,?,?,?,?,?,?)");
                  ps.setString(1,fname1);
                  ps.setString(2,lname1);
                  ps.setString(3,email1);
                  ps.setString(4,phone1);
                  ps.setString(5,user1);
                  ps.setString(6,pass1);
                  ps.setString(7,uidai1);
                  ps.setBlob(8, is);
                  ps.setBoolean(9,false);
                  ps.setBoolean(10,false);
                  int rc = ps.executeUpdate();
                  if(rc==0){
                      out.print("<h3>Record not insert,Retry</h3>");
                  }
                  else{
                      conn.close();
                      //out.print("inserted");
                      ses.setAttribute("uname", request.getParameter("uname"));
                      ses.setAttribute("isAdmin","false");
                      response.sendRedirect("/virtusa-project/api/home");
                  }
                  conn.close();
		  //out.print("Disconnected from database");
		  } catch (Exception e) {
                      out.print(e);
		  }
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
